package com.example.colibrivocebulary.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colibrivocebulary.Adapter.WordAdapter;
import com.example.colibrivocebulary.R;
import com.example.colibrivocebulary.entity.Word;
import com.example.colibrivocebulary.presenter.IWordListView;
import com.example.colibrivocebulary.presenter.WordPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class WordsActivity extends AppCompatActivity implements IWordListView {

    private FloatingActionButton addButton;
    private RecyclerView recyclerView;

    private static WordAdapter wordAdapter;
    private final int ADD_WORD_REQUEST_CODE = 101;

    private WordPresenter wordPresenter = new WordPresenter(this);

    final int VOICE_INPUT_REQUEST = 999;
    private ArrayList<String> resultVoiceIn = new ArrayList<>();

    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar mActionBarToolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);


        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordsActivity.this, AddWordActivity.class);
                startActivityForResult(intent, ADD_WORD_REQUEST_CODE);
            }
        });


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

        if (requestCode == VOICE_INPUT_REQUEST && resultCode == RESULT_OK) {
            resultVoiceIn = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            searchView.setQuery(resultVoiceIn.get(0), false);

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
        getMenuInflater().inflate(R.menu.toolbar_menu_items, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(WordsActivity.this, "Текст:   " + newText, Toast.LENGTH_SHORT).show();
                wordPresenter.searchWordByEnglishVersion(newText);
                return false;
            }
        });

        final MenuItem voiceSearch = menu.findItem(R.id.action_micro);

        voiceSearch.setOnMenuItemClickListener(item -> {
            if (voiceIsEnable()) {
                listenToSpeech();
            }
            return true;
        });
        return true;
    }

    private void listenToSpeech() {

        Intent listenIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        listenIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
        listenIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a word");
        listenIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        listenIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

        startActivityForResult(listenIntent, VOICE_INPUT_REQUEST);
    }

    public boolean voiceIsEnable() {
        PackageManager packManager = getPackageManager();
        List<ResolveInfo> intActivities = packManager.queryIntentActivities(new
                Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

        return intActivities.size() != 0;
    }
}
