package com.example.colibrivocebulary.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.colibrivocebulary.entity.Word;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void insert (Word word);

    @Query("SELECT * FROM Word")
    List<Word> getWords();

    @Delete
    void delete (Word word);

    @Query("SELECT * FROM Word WHERE englishVersion LIKE :filterWord OR russianVersion LIKE :filterWord")
    List<Word> filterByWord(String filterWord);

    @Update
    void update(Word word);



}
