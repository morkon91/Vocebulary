package com.example.colibrivocebulary.Presenter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.colibrivocebulary.App;
import com.example.colibrivocebulary.db.AppDataBase;
import com.example.colibrivocebulary.entity.Word;

public class AddWordPresenter {

    private static AppDataBase appDataBase = App.getAppDataBase();

    private final IAddWordPresenter view;

    public AddWordPresenter(IAddWordPresenter view) {
        this.view = view;
    }

    @SuppressLint("StaticFieldLeak")
    public void addNewWord(final String wordString, final String translationString) {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDataBase.getWordDao().insert(new Word(wordString, translationString));
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                view.onAddWordListSuccess();
            }
        }.execute();

    }


}
