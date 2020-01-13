package com.example.colibrivocebulary.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.colibrivocebulary.App;
import com.example.colibrivocebulary.R;
import com.example.colibrivocebulary.db.AppDataBase;
import com.example.colibrivocebulary.entity.Word;

public class AddWordActivity extends AppCompatActivity {

    private EditText wordEditText;
    private EditText translationEditText;
    private Button saveButton;

    private AppDataBase appDataBase = App.getAppDataBase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        wordEditText = findViewById(R.id.word_edit_text);
        translationEditText = findViewById(R.id.translation_edit_text);
        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wordString = wordEditText.getText().toString();
                String translationString = translationEditText.getText().toString();

                String msg = "Записали слово: " + wordString + "; " + translationString;

                Word word = new Word(wordString, translationString);
                saveToDataBase(word);

                Toast.makeText(AddWordActivity.this, msg, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void saveToDataBase(final Word word){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDataBase.getWordDao().insert(word);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                setResult(RESULT_OK);
                finish();
            }
        }.execute();
    }
}
