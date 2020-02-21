package com.example.colibrivocebulary.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Word {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private long id;
    private String russianVersion;
    private String englishVersion;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return englishVersion.equals(word.englishVersion) &&
                russianVersion.equals(word.russianVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(englishVersion, russianVersion);
    }

    public void setEnglishVersion(String englishVersion) {
        this.englishVersion = englishVersion;
    }

    public void setRussianVersion(String russianVersion) {
        this.russianVersion = russianVersion;
    }

    public String getEnglishVersion() {
        return englishVersion;
    }

    public String getRussianVersion() {
        return russianVersion;
    }

    public Word(String russianVersion, String englishVersion) {
        this.russianVersion = russianVersion;
        this.englishVersion = englishVersion;

    }
}
