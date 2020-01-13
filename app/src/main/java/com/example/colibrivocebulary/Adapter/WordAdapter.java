package com.example.colibrivocebulary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colibrivocebulary.R;
import com.example.colibrivocebulary.entity.Word;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
    private List<Word> words;

    public WordAdapter() {
        words = new ArrayList<>();
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private TextView englishVersionTextView;
        private TextView russianVersionTextView;

        public WordViewHolder(@NonNull View itemView) {
            super(itemView);

            englishVersionTextView = itemView.findViewById(R.id.english_version_text_view);
            russianVersionTextView = itemView.findViewById(R.id.russian_version_text_view);
        }

        public void bind(Word word) {
            englishVersionTextView.setText(word.getEnglishVersion());
            russianVersionTextView.setText(word.getRussianVersion());
        }
    }


    public void setWords(List<Word> words) {
        if (!this.words.isEmpty()) {
            this.words.clear();
        }

        this.words.addAll(words);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.word_item_view, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        Word word = words.get(position);
        holder.bind(word);
    }

    @Override
    public int getItemCount() {
        return words.size();
    }


}
