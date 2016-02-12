package com.sweproject.calorietracker.DataAccessLayer;

import android.database.sqlite.SQLiteDatabase;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by mbyrd_000 on 2/9/2016.
 */
public abstract class LoadDatabase implements ILoadDatabase
{
    protected static final String DATABASE_NAME = "CalorieCounter.db";
    protected static final String FOODS_TABLE_NAME = "Foods";
    protected SQLiteDatabase db;

    LoadDatabase()
    {

    }

    public void OnInstallation()
    {
        CreateDb();
        CreateTables();
    }

    private void CreateTables()
    {
        CreateFoodsTable();
        CreateServingSizeDayTable();
        CreateUsersTable();
        CreateDaysTable();
        CreateServingSizeDayTable();
    }

    private void CreateDb()
    {
        Connection connection = null;
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:CalorieCounter.db");
        }
        catch(Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(-1);
        }
        System.out.println("Successfully Created CalorieCounter.db");
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

    private void createServingSizesTable()
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
}
