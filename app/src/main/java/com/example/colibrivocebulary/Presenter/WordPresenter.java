package com.example.colibrivocebulary.Presenter;


import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.colibrivocebulary.App;
import com.example.colibrivocebulary.db.AppDataBase;
import com.example.colibrivocebulary.entity.Word;

import java.util.List;

public class WordPresenter {

    private static AppDataBase appDataBase = App.getAppDataBase();

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




}
