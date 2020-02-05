package com.example.colibrivocebulary.presenter;

import com.example.colibrivocebulary.entity.Word;

import java.util.List;

public interface IWordListView {

    void onLoadWordListSuccess(List<Word> words);

    void onDeleteWordSuccess(Word word);

    void onSearchWordListSuccessByEnglishVersion(List<Word> searchedWords);
}
