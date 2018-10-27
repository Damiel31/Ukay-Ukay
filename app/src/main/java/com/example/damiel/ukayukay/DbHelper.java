package com.example.damiel.ukayukay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by Damiel on 10/22/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String tbluser = "User", tbluserid = "Userid",tblusername = "Username",
            tblpassword = "Password", tblname = "Name";



    public DbHelper(Context context) {
        super(context, "database", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT)",
                tbluser,tbluserid,tblusername,tblpassword,tblname);

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long createUser(HashMap<String, String> map_user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues val = new ContentValues();

        String sql = "SELECT * FROM " + tbluser + " WHERE " + tblusername + "= '" + map_user.get("Username") + "' ";
        Cursor cur = db.rawQuery(sql, null);
        long UserID = 0;

        if (cur.moveToNext()){
            UserID = 0;
        }
        else{
            val.put(tblusername, map_user.get("Username"));
            val.put(tblpassword, map_user.get("Password"));
            val.put(tblname,map_user.get("Name"));

            UserID = db.insert(tbluser,null,val);
        }

        db.close();
        cur.close();

        return UserID;
    }

    public long checkuser(String Username, String Password){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + tbluser + " WHERE " + tblusername + "= '" + Username + "' AND " + tblpassword + "= '" + Password + "' ";
        Cursor cur = db.rawQuery(sql,null);
        long UserID = 0;

        if (cur.moveToNext()){
            UserID = cur.getInt(cur.getColumnIndex(tbluserid));
        }
        db.close();
        cur.close();
        return UserID;
    }


}