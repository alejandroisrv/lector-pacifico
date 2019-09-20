package com.example.pacificoreader.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbManager extends SQLiteOpenHelper {

    private static final int VERSION = 1 ;
    private static final String DATABASE_NAME = "siip.db";
    private final String TABLE;
    private final String[] COLUMNS;


    public DbManager(Context context, String table, String[] columns) {
        super(context, DATABASE_NAME, null, VERSION);
        this.TABLE = table;
        this.COLUMNS = columns;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sql = "CREATE TABLE " + TABLE + " (" + BaseColumns._ID + " integer primary key autoincrement ";

        for (int i=0;i< COLUMNS.length; i++) {
            sql+= " ," + COLUMNS[i];
        }

        sql+=" ) ";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
    }

    public void insert(ContentValues values){
        getWritableDatabase().insert(TABLE, null, values);
    }

    public Cursor getAll(){
        return getReadableDatabase().query(TABLE, null, null, null, null, null, null);
    }
}
