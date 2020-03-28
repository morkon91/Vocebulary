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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DialogAddWord extends DialogFragment implements View.OnClickListener {

    TextInputLayout translatedNewWordEditText;
    TextInputLayout newWordEditText;;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.dialog_add_word, null);

        view.findViewById(R.id.getTranslateButton).setOnClickListener(this::onClick);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        translatedNewWordEditText = view.findViewById(R.id.translatedWordEditText);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getTranslateButton:
                translatedNewWordEditText.setVisibility(View.VISIBLE);
                break;

        }
    }
}
