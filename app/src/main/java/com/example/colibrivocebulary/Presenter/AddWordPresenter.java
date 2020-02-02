package com.example.colibrivocebulary.Presenter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.colibrivocebulary.App;
import com.example.colibrivocebulary.db.AppDataBase;
import com.example.colibrivocebulary.entity.Word;

import java.util.List;

public class AddWordPresenter {

    private static AppDataBase appDataBase = App.getAppDataBase();

    private final IAddWordPresenter view;
    private Word newWord;
    private String msg;



    public AddWordPresenter(IAddWordPresenter view) {
        this.view = view;
    }

    @SuppressLint("StaticFieldLeak")
    public void addNewWord(final String wordString, final String translationString) {



        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                List<Word> listofWords = appDataBase.getWordDao().getWords();
                newWord = new Word(wordString, translationString);

                if (!equalsWords(newWord,listofWords)){
                    appDataBase.getWordDao().insert(new Word(wordString, translationString));
                    msg = "Слово " + wordString + " : " + translationString + " добавлено в словарь";
                }
                else
                    msg = "Слово " + wordString + " : " + translationString + " Уже есть в словаре";
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                view.onAddWordListSuccess(msg);
            }
        }.execute();
    }


    //Проверяем, есть ли в базе данных введенное пользователем слово: true - есть, false - нет
    private boolean equalsWords(Word word, List<Word> words) {
        for (Word w : words) {
            if (w.equals(word)) {
                return true;
            }
        }
        return false;
    }


}
