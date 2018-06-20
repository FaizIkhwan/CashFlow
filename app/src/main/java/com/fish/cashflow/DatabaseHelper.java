package com.fish.cashflow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
    //log
    private static String TAG = "DatabaseHelper";

    //Database name
    public static final String DATABASE_NAME = "Cashflow.db";

    //Table names
    public static final String TABLE_NAME_EXPENSE = "Expense";
    public static final String TABLE_NAME_INCOME = "Income";
    public static final String TABLE_NAME_CATEGORY = "Category";
    public static final String TABLE_NAME_WISHLIST = "Wishlist";

    //Table EXPENSE
    public static final String COL_1_E = "ID";
    public static final String COL_2_E = "EXPENSE"; //int
    public static final String COL_3_E = "DESCRIPTION"; //string
    public static final String COL_4_E = "DATE"; //string
    public static final String COL_5_E = "CATEGORY"; //string

    //Table INCOME
    public static final String COL_1_I = "ID";
    public static final String COL_2_I = "INCOME"; //int
    public static final String COL_3_I = "MONTH"; //string

    //Table CATEGORY
    public static final String COL_1_C = "ID";
    public static final String COL_2_C = "DESCRIPTION"; //string
    public static final String COL_3_C = "BUDGET"; //int
    public static final String COL_4_C = "STATE"; // (string)   BOOLEAN untuk check category itu dipilih atau tak.

    //Table WISHLIST
    public static final String COL_1_W = "ID";
    public static final String COL_2_W = "DESCRIPTION"; // string
    public static final String COL_3_W = "PRICE"; //int

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //creating required tables
        db.execSQL("create table " + TABLE_NAME_EXPENSE +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, EXPENSE INTEGER, DESCRIPTION TEXT, DATE TEXT, CATEGORY TEXT)");
        db.execSQL("create table " + TABLE_NAME_INCOME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, INCOME INTEGER, MONTH TEXT)");
        db.execSQL("create table " + TABLE_NAME_CATEGORY +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, DESCRIPTION TEXT, BUDGET INTEGER, STATE TEXT)");
        db.execSQL("create table " + TABLE_NAME_WISHLIST +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, DESCRIPTION TEXT, PRICE INTEGER)");
        Log.d(TAG, "DONE CREATE 4 TABLE !");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_INCOME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_WISHLIST);

        //create new tables
        onCreate(db);
    }

    public boolean insertDataExpense(String expense, String description, String date, String category)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_E, expense);
        contentValues.put(COL_3_E, description);
        contentValues.put(COL_4_E, date);
        contentValues.put(COL_5_E, category);
        long res = db.insert(TABLE_NAME_EXPENSE, null, contentValues);
        if(res == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataIncome(String income, String month)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_I, income);
        contentValues.put(COL_3_I, month);
        long res = db.insert(TABLE_NAME_INCOME, null, contentValues);
        if(res == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataCategory(String description, String budget, String state)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_C, description);
        contentValues.put(COL_3_C, budget);
        contentValues.put(COL_4_C, state);
        long res = db.insert(TABLE_NAME_CATEGORY, null, contentValues);
        if(res == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataWishlist(String description, String price)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_W, description);
        contentValues.put(COL_3_W, price);
        long res = db.insert(TABLE_NAME_WISHLIST, null, contentValues);
        if(res == -1)
            return false;
        else
            return true;
    }

    public boolean updateDataCategory(String id, String description, String budget, String state) //overwrite table category
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_C, description);
        contentValues.put(COL_3_C, budget);
        contentValues.put(COL_4_C, state);

        //updating rows
        db.update(TABLE_NAME_CATEGORY, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public boolean updateMonthlyIncome(String id, String income, String month) //overwrite table income
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_I, income);
        contentValues.put(COL_3_I, month);

        //updating rows
        db.update(TABLE_NAME_INCOME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    public Cursor getMonthlyIncome(String month) // akan return monthly income berdasarkan bulan yang ditanya
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select "+ COL_1_I +", " +COL_2_I+ " from "+ TABLE_NAME_INCOME +" where " + COL_3_I + " = " + " '" +month+ "'";
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    public Cursor getAllData() // currently not using. Get all data from table expense
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from "+TABLE_NAME_EXPENSE;
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    public Cursor getCategoryDataFromExpense(String cat) // query "select * from expense where category = var(cat) order by date "
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from " +TABLE_NAME_EXPENSE+ " where " +COL_5_E+ " = "+" '"+cat+"'"+" order by "+COL_4_E+" desc";
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    public Cursor getStateForCategory(String cat) // query untuk dapat kan state for every category
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select "+ COL_2_C + ", " + COL_4_C +" from "+ TABLE_NAME_CATEGORY +" where " + COL_2_C + " = " + " '" +cat+ "'";
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    public Cursor getBudgetOnlyForCategory(String cat) // query untuk dapat kan budget je for every category
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select "+ COL_3_C  +" from "+ TABLE_NAME_CATEGORY +" where " + COL_2_C + " = " + " '" +cat+ "'";
        Cursor res = db.rawQuery(query,null);
        return res;
    }
}
