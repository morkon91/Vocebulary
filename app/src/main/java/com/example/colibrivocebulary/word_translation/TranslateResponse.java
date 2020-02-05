package com.example.colibrivocebulary.word_translation;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TranslateResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("lang")
    private String lang;

    @SerializedName("text")
    private List<String> textList = new ArrayList<>();

    public int getCode() {
        return code;
    }

    public String getLang() {
        return lang;
    }

    public List<String> getTextList() {
        return textList;
    }
}
