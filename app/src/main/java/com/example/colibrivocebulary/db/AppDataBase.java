package com.example.colibrivocebulary.db;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.colibrivocebulary.entity.Word;

@Database(entities = Word.class, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract WordDao getWordDao();

}


