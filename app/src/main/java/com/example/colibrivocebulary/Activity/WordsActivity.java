package com.example.colibrivocebulary.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.example.colibrivocebulary.Presenter.IWordListView;
import com.example.colibrivocebulary.Presenter.WordPresenter;
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
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("Очень странный словарик :-)");
        Objects.requireNonNull(getSupportActionBar()).setTitle("Переводчик");

        searchEditText = findViewById(R.id.search_edit_text);
        searchEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        recyclerView = findViewById(R.id.word_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        wordAdapter = new WordAdapter(this);
        recyclerView.setAdapter(wordAdapter);

        loadWords();

        wordAdapter.setOnPopupClickListener(new WordAdapter.onPopupClickListener() {
            @Override
            public void deleteWord(Word word) {
                Toast.makeText(WordsActivity.this, "Запись будет удалена потом " + word.getRussianVersion(), Toast.LENGTH_LONG).show();
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
    public void onLoadWordListProgress() {
        //no op
    }


    //Обработка нажатий на всплывающее меню в RecyclerView (PopupMenu)
//    @Override
//    public void deleteWord() {
//
//    }
//
//    @Override
//    public void editWord() {
//
//    }


    //Работа с менюшкой тулбара

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (searchEditText.getVisibility() != View.VISIBLE) {
            searchEditText.setVisibility(View.VISIBLE);

        } else {
            searchEditText.setVisibility(View.GONE);
        }
        return true;
    }


}
