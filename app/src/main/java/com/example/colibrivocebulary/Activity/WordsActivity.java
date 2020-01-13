package com.example.colibrivocebulary.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;

import com.example.colibrivocebulary.Adapter.WordAdapter;
import com.example.colibrivocebulary.App;
import com.example.colibrivocebulary.R;
import com.example.colibrivocebulary.db.AppDataBase;
import com.example.colibrivocebulary.entity.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class WordsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText searchEditText;
    private FloatingActionButton addButton;
    private RecyclerView recyclerView;

    private WordAdapter wordAdapter;

    private AppDataBase appDataBase = App.getAppDataBase();

    private static final int ADD_WORD_REQUEST_CODE = 101;

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
        Objects.requireNonNull(getSupportActionBar()).setSubtitle("Очень странный переводчик )))");
        Objects.requireNonNull(getSupportActionBar()).setTitle("Переводчик");

        searchEditText = findViewById(R.id.search_edit_text);
        searchEditText.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        recyclerView = findViewById(R.id.word_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        wordAdapter = new WordAdapter();
        recyclerView.setAdapter(wordAdapter);

        loadWords();
    }

    @SuppressLint("StaticFieldLeak")
    private void loadWords(){
        new AsyncTask<Void, Void, List<Word>>() {
            @Override
            protected List<Word> doInBackground(Void... voids) {
                return appDataBase.getWordDao().getWords();
            }

            @Override
            protected void onPostExecute(List<Word> words) {
                wordAdapter.setWords(words);
            }
        }.execute();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_WORD_REQUEST_CODE && resultCode == RESULT_OK) {
            loadWords();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (searchEditText.getVisibility() != View.VISIBLE) {
            searchEditText.setVisibility(View.VISIBLE);

        }else {
            searchEditText.setVisibility(View.GONE);
        }
        return true;
    }
}
