/*
Authors: Taylor Hooser, Patrick Cruz
Date: Spring 2023
Purpose: Creates and manages local database
*/

package com.example.reminderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public static final String DBName = "Login.db";

    public Database(Context context) {
        super(context, "Login.db", null, 1 );
    }

    // creates database if it doesnt already exist
    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)");
    }

    // runs when DB needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    // inserting data
    public boolean insertData (String username, String password){
        SQLiteDatabase MyBD = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyBD.insert("users",null,contentValues);
        if(result == -1) return false;
        else
            return true;
    }

    // checks if username exists
    public boolean checkusername (String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    // check if username/password combo is correct
    public boolean checkusernamepassword (String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username, password});
        if (cursor.getCount()>0)
            return true;
        else
            return  false;
    }
}