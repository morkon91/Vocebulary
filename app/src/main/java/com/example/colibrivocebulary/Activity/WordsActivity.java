package com.example.colibrivocebulary.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colibrivocebulary.Adapter.WordAdapter;
import com.example.colibrivocebulary.presenter.IWordListView;
import com.example.colibrivocebulary.presenter.WordPresenter;
import com.example.colibrivocebulary.R;
import com.example.colibrivocebulary.entity.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class WordsActivity extends AppCompatActivity implements IWordListView {

    private Toolbar toolbar;
    private EditText searchEditText;
    private FloatingActionButton addButton;
    private RecyclerView recyclerView;

    private static WordAdapter wordAdapter;
    private final int ADD_WORD_REQUEST_CODE = 101;

    private WordPresenter wordPresenter = new WordPresenter(this);

    private final TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            wordPresenter.searchWordByEnglishVersion(searchEditText.getText().toString());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordsActivity.this, AddWordActivity.class);
                startActivityForResult(intent, ADD_WORD_REQUEST_CODE);
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("Очень странный словарик :-)");
        Objects.requireNonNull(getSupportActionBar()).setTitle("Твой словарик");

        searchEditText = findViewById(R.id.search_edit_text);
        searchEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        searchEditText.addTextChangedListener(searchTextWatcher);

        recyclerView = findViewById(R.id.word_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        wordAdapter = new WordAdapter(this);
        recyclerView.setAdapter(wordAdapter);

        loadWords();


        // Обработка нажатий на позиции в списке слов (RecyclerView)
        wordAdapter.setOnPopupClickListener(new WordAdapter.onPopupClickListener() {
            @Override
            public void deleteWord(Word word) {
                wordPresenter.deleteWordFromList(word);

            }

            @Override
            public void editWord(Word word) {
                Toast.makeText(WordsActivity.this, "Запись будет изменена потом " + word.getRussianVersion(), Toast.LENGTH_LONG).show();
            }
        });


    }

    public void loadWords() {
        wordPresenter.loadWordList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_WORD_REQUEST_CODE && resultCode == RESULT_OK) {
            loadWords();

        }
    }

    @Override
    public void onLoadWordListSuccess(List<Word> words) {
        wordAdapter.setWords(words);

    }

    @Override
    public void onDeleteWordSuccess(Word word) {
        Toast.makeText(WordsActivity.this, "Удалено слово: " + word.getRussianVersion(), Toast.LENGTH_LONG).show();
        loadWords();
    }

    @Override
    public void onSearchWordListSuccessByEnglishVersion(List<Word> searchedWords) {
        wordAdapter.setWords(searchedWords);
    }


    //Работа с менюшкой тулбара

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (searchEditText.getVisibility() != View.VISIBLE) {
            searchEditText.setVisibility(View.VISIBLE);
            searchEditText.requestFocus();
            im.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
        } else {
            searchEditText.setVisibility(View.GONE);
            im.hideSoftInputFromWindow(searchEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return true;
    }

}
