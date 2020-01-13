package com.example.colibrivocebulary;

import android.app.Application;

import androidx.room.Room;

import com.example.colibrivocebulary.db.AppDataBase;

public class App extends Application {

    private static AppDataBase appDataBase;


    public static AppDataBase getAppDataBase() {
        return appDataBase;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appDataBase = Room
                .databaseBuilder(this, AppDataBase.class, "app-database")
                .build();
    }



}
