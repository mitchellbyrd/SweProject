package com.sweproject.calorietracker.DataAccessLayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by mbyrd_000 on 2/9/2016.
 */
public class DatabaseLoader extends SQLiteOpenHelper implements IDatabaseLoader
{
    protected static final String DATABASE_NAME = "CalorieCounter.db";
    protected SQLiteDatabase db;

    public DatabaseLoader(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        db = sqLiteDatabase;
        CreateTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        DropTableIfExists("ServingSize_Day");
        DropTableIfExists("Days");
        DropTableIfExists("Users");
        DropTableIfExists("ServingSizes");
        DropTableIfExists("Foods");

        onCreate(db);
    }

    private void CreateTables()
    {
        CreateFoodsTable();
        CreateServingSizesTable();
        CreateUsersTable();
        CreateDaysTable();
        CreateServingSizeDayTable();
    }


    private void CreateDb()
    {
        DropTableIfExists("Foods");

        try {
            db.execSQL("create table Foods (\n" +
                    "  Id integer primary key\n" +
                    ", Name text\n" +
                    ", LastConsumed text\n" +
                    ", Usda integer);");
        }
        catch(Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(-1);
        }
    }

    private void CreateFoodsTable()
    {
        DropTableIfExists("Foods");

        try {
            db.execSQL("create table Foods (\n" +
                    "  Id integer primary key\n" +
                    ", Name text\n" +
                    ", LastConsumed text\n" +
                    ", Usda );");
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Created Foods Table");
    };

    private void CreateServingSizesTable()
    {
        DropTableIfExists("ServingSizes");

        try {
            db.execSQL("create table ServingSizes (\n" +
                    "  Id integer primary key\n" +
                    ", FoodId integer\n" +
                    ", ServingSizeType text\n" +
                    ", Amount real\n" +
                    ", Calories real\n" +
                    ", Proteins real\n" +
                    ", Carbs real\n" +
                    ", Fats real\n" +
                    ", foreign key (FoodId) references Foods(Id) );");
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Created ServingSizes Table");
    }

    private void CreateUsersTable()
    {
        DropTableIfExists("Users");

        try {
            db.execSQL("create table Users (\n" +
                    "  Id integer primary key\n" +
                    ", EmailAddress text\n" +
                    ", Password text\n" +
                    ", NameFirst text\n" +
                    ", NameLast text\n" +
                    ", BirthDate text\n" +
                    ", Weight real\n" +
                    ", PreferedCalorieLimit real\n" +
                    ", PreferedFatLimit real\n" +
                    ", PreferedCarbLimit real\n" +
                    ", PreferedProteinLimit real );");
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Created Users Table");
    }

    private void CreateDaysTable()
    {
        DropTableIfExists("Days");

        try {
            db.execSQL("create table Days (\n" +
                    "  Id integer primary key\n" +
                    ", UserId integer\n" +
                    ", Date text\n" +
                    ", CurrentTotalCalorie real\n" +
                    ", CurrentTotalFat real\n" +
                    ", CurrentTotalCarb real\n" +
                    ", CurrentTotalProtein real\n" +
                    ", GoalTotalCalorie real\n" +
                    ", GoalTotalFat real\n" +
                    ", GoalTotalCarb real\n" +
                    ", GoalTotalProtein real\n" +
                    ", foreign key (UserId) references Users(Id) );");
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Created Days Table");
    }

    private void CreateServingSizeDayTable()
    {
        DropTableIfExists("ServingSize_Day");

        try {
            db.execSQL("create table ServingSize_Day (\n" +
                    "  Id integer primary key\n" +
                    ", ServingSizesId integer\n" +
                    ", DayId integer\n" +
                    ", NumberOfServings real\n" +
                    ", foreign key (ServingSizesId) references ServingSizes(Id)\n" +
                    ", foreign key (DayId) references Days(Id) );");
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Created ServingSize_Day Table");
    }

    private void DropTableIfExists(String tableName)
    {
        try {
            db.execSQL("drop table if exists " + tableName + ";");
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Successfully dropped " + tableName + " table");
    }

    private void SelectTester()
    {
        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Id from Foods;", null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false) {
            arrayList.add(cursor.getString(cursor.getColumnIndex("Id")));
            cursor.moveToNext();
        }

        db = this.getWritableDatabase();
        db.execSQL("insert into Foods (Id, Name, LastConsumed, Usda) values (1, 'food', 'now', 2015);");

        db = this.getReadableDatabase();
        cursor = db.query("Foods", new String[] {"Id", "Name", "LastConsumed", "Usda"}, null,null,null,null,null );
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false) {
            arrayList.add(cursor.getString(cursor.getColumnIndex("Id")));
            arrayList.add(cursor.getString(cursor.getColumnIndex("Name")));
            arrayList.add(cursor.getString(cursor.getColumnIndex("LastConsumed")));
            int i = (cursor.getInt(cursor.getColumnIndex("Usda")));
            cursor.moveToNext();
        }
    }
}
