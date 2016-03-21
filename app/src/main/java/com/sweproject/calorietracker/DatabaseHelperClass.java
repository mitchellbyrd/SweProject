package com.sweproject.calorietracker;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cthoughts on 3/15/2016.
 */
public class DatabaseHelperClass extends SQLiteOpenHelper
{
    // Database,Version and Table
    public static final String DATABASE_NAME = "food.db";
    private static final int DB_VERSION = 2;
    public static final String TABLE_NAME = "foodTable";

    //columns
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FOOD_NAME";
    public static final String COL_3 = "CALORIES";
    public static final String COL_4 = "CARBS";
    public static final String COL_5 = "PROTIEN";

    public String tag = "test";

    // create table
    private static final String TABLE_CREATE = "CREATE TABLE"+TABLE_NAME+" ("+
            COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COL_2+" TEXT, "+
            COL_3+" TEXT, "+
            COL_4+" TEXT, "+
            COL_5+" TEXT, "+")";


    public DatabaseHelperClass(Context context)
    {
        super(context,DATABASE_NAME,null,DB_VERSION);
        Log.i(tag,"Database created");
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        Log.i(tag,"Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
            // doesnt create the table if it already exist
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_CREATE);
        onCreate(db);
        Log.i(tag,"Created update");
    }

    public boolean insertData(String foodName,String foodCal,String foodCarbs,String foodProtien)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,foodName);
        contentValues.put(COL_3,foodCal);
        contentValues.put(COL_4,foodCarbs);
        contentValues.put(COL_5, foodProtien);

     long result =   db.insert(TABLE_NAME,null,contentValues);

      //  long result = db.insert(TABLE_NAME,null,contentValues);

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
