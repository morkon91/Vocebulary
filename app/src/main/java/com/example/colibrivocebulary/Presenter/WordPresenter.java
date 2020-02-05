package com.example.colibrivocebulary.Presenter;


import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.colibrivocebulary.App;
import com.example.colibrivocebulary.db.AppDataBase;
import com.example.colibrivocebulary.entity.Word;

import java.util.List;

public class WordPresenter {

    private AppDataBase appDataBase = App.getAppDataBase();

    private final IWordListView view;

    public WordPresenter(IWordListView view) {
        this.view = view;
    }

    @SuppressLint("StaticFieldLeak")
    public void loadWordList(){

        new AsyncTask<Void, Void, List<Word>>() {
            @Override
            protected List<Word> doInBackground(Void... voids) {
                return appDataBase.getWordDao().getWords();
            }

            @Override
            protected void onPreExecute() {
                view.onLoadWordListProgress();
            }

            @Override
            protected void onPostExecute(List<Word> words) {
                view.onLoadWordListSuccess(words);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteWordFromList(final Word word) {

        new AsyncTask<Void, Void, Word>() {


            @Override
            protected Word doInBackground(Void... voids) {
                appDataBase.getWordDao().delete(word);
                return word;
            }

            @Override
            protected void onPostExecute(Word word) {
                view.onDeleteWordSuccess(word);
            }
        }.execute();
    }

    public void searchWordByEnglishVersion(String searchEnglishVersion) {

    }
}
