package com.example.pacificoreader.Libro.SqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.provider.BaseColumns;

import com.example.pacificoreader.DB.DbManager;

import java.util.ArrayList;

public class LibroSqlAdapter extends DbManager {


    private static final String TABLE = LibroEntry.TABLE_NAME;
    private static final String[] COLUMNS = LibroEntry.COLUMNS;

    public LibroSqlAdapter(Context context) {
        super(context, TABLE, COLUMNS);
    }

    public void insert( ArrayList<String> params) {
        ContentValues values = new ContentValues();

        values.put( "nombre", params.get(0));
        values.put( "descripcion", params.get(1));
        values.put( "autor", params.get(2));
        values.put( "path", params.get(3));
        values.put( "imagen", params.get(4));


        super.insert(values);
    }

    public final static class LibroEntry implements BaseColumns {

        public static final String TABLE_NAME ="libros";
        public static final String COLUMNA_NOMBRE = "nombre varchar(150) NOT NULL";
        public static final String COLUMNA_DESCRIPCION = "descripcion TEXT NOT NULL";
        public static final String COLUMNA_IMAGE = "imagen varchar(150) NOT NULL";
        public static final String COLUMNA_AUTOR = "autor char(36) NOT NULL";
        public static final String COLUMNA_PATH = "path TEXT NOT NULL";

        public  static final String[] COLUMNS = { COLUMNA_NOMBRE,COLUMNA_DESCRIPCION,COLUMNA_IMAGE,COLUMNA_AUTOR,COLUMNA_PATH };

    }


}
