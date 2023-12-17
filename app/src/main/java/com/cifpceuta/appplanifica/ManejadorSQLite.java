package com.cifpceuta.appplanifica;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ManejadorSQLite extends SQLiteOpenHelper {
    private static final String SQL_CREAR_ENTRADAS =
            "CREATE TABLE "+ DefinicionBD.Entradas.NOMBRE_TABLA+" ("+
                    DefinicionBD.Entradas.COL_EMAIL + " TEXT PRIMARY KEY,"+
                    DefinicionBD.Entradas.COL_PASSWORD + " TEXT)";
    private static final String SQL_BORRAR_ENTRADAS =
            "DROP TABLE IF EXISTS "+ DefinicionBD.Entradas.NOMBRE_TABLA;
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "usuarios.db";
    public ManejadorSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAR_ENTRADAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_BORRAR_ENTRADAS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}
