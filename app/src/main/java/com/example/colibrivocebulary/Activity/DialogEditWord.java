package com.example.colibrivocebulary.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.colibrivocebulary.R;

public class DialogEditWord extends DialogFragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.dialog_edit_word, null);

        view.findViewById(R.id.button_save_edited_word).setOnClickListener(this);
        view.findViewById(R.id.button_cancel_edit_word).setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save_edited_word:
                Toast.makeText(getContext(), "Сохранили изменения", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.button_cancel_edit_word:
                Toast.makeText(getContext(), "Не сохранили изменения", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }


}
