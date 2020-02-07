package com.example.colibrivocebulary.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colibrivocebulary.presenter.IAddWordView;
import com.example.colibrivocebulary.R;
import com.example.colibrivocebulary.word_translation.ITranslateWordView;
import com.example.colibrivocebulary.word_translation.YandexTranslate;

public class AddWordActivity extends AppCompatActivity implements IAddWordView, ITranslateWordView {

    private EditText wordEditText;
    private EditText translationEditText;
    private Button saveButton;
    private Button translateButton;

    private ProgressBar translateProgressBar;


    private YandexTranslate translateYandex;

    private com.example.colibrivocebulary.presenter.AddWordPresenter addWordPresenter = new com.example.colibrivocebulary.presenter.AddWordPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        wordEditText = findViewById(R.id.word_edit_text);
        translationEditText = findViewById(R.id.translation_edit_text);
        saveButton = findViewById(R.id.save_button);
        translateButton = findViewById(R.id.translate_button);
        translateProgressBar = findViewById(R.id.translate_progress_circular);

        saveButton.setOnClickListener(v -> {
            String wordString = wordEditText.getText().toString();
            String translationString = translationEditText.getText().toString();
            addWordPresenter.addNewWord(wordString, translationString);
        });

        translateYandex = new YandexTranslate(AddWordActivity.this);

        //Перевод с Яндекс.Переводчиком
        translateButton.setOnClickListener(v -> {
            String wordString = wordEditText.getText().toString();
            if (true) {

                translateYandex.getTranslate_EN_RU(wordString);
            }else if (wordString.isEmpty()){
                Toast.makeText(AddWordActivity.this, "Ошибка! Введите, пожалуста, слово!",
                        Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(AddWordActivity.this, "Ошибка! Введите, слово на иностранном языке!",
                        Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
            translateYandex.cancelTask();
    }

    @Override
    public void onTranslateWordSuccess(String s) {
        translationEditText.setText(s);
        translateProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSearchTranslateWordProgress() {
        translateProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAddWordListSuccess(String msg) {
        setResult(RESULT_OK);
        Toast.makeText(AddWordActivity.this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }
}
