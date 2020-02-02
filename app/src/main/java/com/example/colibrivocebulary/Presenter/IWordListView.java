package com.example.colibrivocebulary.Presenter;

import com.example.colibrivocebulary.entity.Word;

import java.util.List;

public interface IWordListView {
    void onLoadWordListProgress();

    void onLoadWordListSuccess(List<Word> words);

    void onDeleteWordSuccess(Word word);
}
