package com.sweproject.calorietracker;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by cthoughts on 3/15/2016.
 */
public class DatabaseHelperClass extends SQLiteOpenHelper
{
    // Database and table
    public static final String DATABASE_NAME = "food.db";
    public static final String TABLE_NAME = "food_table";

    //colums
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FOOD_NAME";
    public static final String COL_3 = "CALORIES";
    public static final String COL_4 = "CARBS";
    public static final String COL_5 = "PROTIEN";

    public DatabaseHelperClass(Context context)
    {
        super(context,DATABASE_NAME,null, 1);

    }



    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // CREATE TABLE
            db.execSQL("create table" + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,FOOD_NAME TEXT, CALORIES TEXT, CARBS TEXT, PROTIEN TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
            // doesnt create the table if it already exist
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String foodName,String foodCal,String foodCarbs,String foodProtien)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,foodName);
        contentValues.put(COL_2,foodCal);
        contentValues.put(COL_2,foodCarbs);
        contentValues.put(COL_2, foodProtien);

        db.insert(TABLE_NAME,null,contentValues);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }
}
