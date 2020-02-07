package com.example.colibrivocebulary.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colibrivocebulary.R;
import com.example.colibrivocebulary.entity.Word;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
    private List<Word> words;
    private Context mContext;

    private onPopupClickListener onPopupClickListener;

    public void setOnPopupClickListener(onPopupClickListener onPopupClickListener) {
        this.onPopupClickListener = onPopupClickListener;
    }

    public WordAdapter(Context mContext) {
        words = new ArrayList<>();
        this.mContext = mContext;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private TextView englishVersionTextView;
        private TextView russianVersionTextView;

        WordViewHolder(@NonNull View itemView) {
            super(itemView);

            englishVersionTextView = itemView.findViewById(R.id.english_version_text_view);
            russianVersionTextView = itemView.findViewById(R.id.russian_version_text_view);
        }

        void bind(Word word) {
            englishVersionTextView.setText(word.getEnglishVersion());
            russianVersionTextView.setText(word.getRussianVersion());
        }
    }


    public void setWords(List<Word> words) {
        this.words.clear();
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
    public void onBindViewHolder(@NonNull final WordViewHolder holder, int position) {
        final Word word = words.get(position);
        holder.bind(word);

        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(mContext, holder.itemView);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_item_delete:
                        onPopupClickListener.deleteWord(word);
                        break;
                    case R.id.menu_item_egit:
                        onPopupClickListener.editWord(word);
                        break;
                    default:
                        break;
                }
                return false;
            });
            popupMenu.show();
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return words.size();
    }


    public interface onPopupClickListener {
        void deleteWord(Word word);

        void editWord(Word word);
    }
}
