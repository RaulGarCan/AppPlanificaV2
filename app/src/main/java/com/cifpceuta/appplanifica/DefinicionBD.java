package com.cifpceuta.appplanifica;

import android.provider.BaseColumns;

public final class DefinicionBD {
    private DefinicionBD(){}
    public static class Entradas implements BaseColumns {
        public static final String NOMBRE_TABLA = "";
        public static final String NOMBRE_COL1 = "";
        public static final String NOMBRE_COL2 = "";
    }
    private static final String SQL_CREAR_ENTRADAS =
            "CREATE TABLE "+Entradas.NOMBRE_TABLA+" ("+
            Entradas._ID + " INTEGER PRIMARY KEY,"+
            Entradas.NOMBRE_COL1 + " TEXT,"+
            Entradas.NOMBRE_COL2 + " TEXT)";
    private static final String SQL_BORRAR_ENTRADAS =
            "DROP TABLE IF EXISTS "+Entradas.NOMBRE_TABLA;
}
