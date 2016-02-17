package com.sweproject.calorietracker.DataAccessLayer;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by mbyrd_000 on 2/9/2016.
 */
public interface IDatabaseLoader {
    void onCreate(SQLiteDatabase sqLiteDatabase);
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
