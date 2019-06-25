package com.farhanrozali.cgpacalculator;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbGPA extends SQLiteOpenHelper {

    public static final String dbName = "dbGpaCalc";
    public static final String tblName = "dbmygpa";
    public static final String tblName2 = "dbmysubj";
    public static final String colGpaId = "gpa_id";
    public static final String colGpaNo = "gpa_no";
    public static final String colGpaTotal = "gpa_total";
    public static final String colSubjId = "subj_id";
    public static final String colSubjName = "subj_name";
    public static final String colSubjGred = "subj_gred";
    public static final String colSubjCredit = "subj_credit";
    public static final String colSubjGpaNo = "subjgpa_no";

    public dbGPA(Context context)
    {
        super(context,dbName,null,5);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE IF NOT EXISTS dbmygpa(gpa_id INTEGER PRIMARY KEY AUTOINCREMENT,gpa_no INTEGER,gpa_total REAL);");
        db.execSQL("CREATE TABLE IF NOT EXISTS dbmysubj(subj_id INTEGER PRIMARY KEY AUTOINCREMENT,subj_name VARCHAR,subj_gred VARCHAR, " +
                "subj_credit INTEGER, subjgpa_no INTEGER, FOREIGN KEY (subjgpa_no) REFERENCES dbmygpa(gpa_no));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS dbmygpa");
        db.execSQL("DROP TABLE IF EXISTS dbmysubj");
        onCreate(db);
    }

    public void fnExecuteSql(String strSql, Context appContext)
    {
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL(strSql);
        } catch (Exception e){
            Log.d("Unable to run query", "Error!");
        }
    }

}
