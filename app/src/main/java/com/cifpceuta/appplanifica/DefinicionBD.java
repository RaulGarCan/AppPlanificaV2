package com.cifpceuta.appplanifica;

import android.provider.BaseColumns;

public final class DefinicionBD {
    private DefinicionBD(){}
    public static class Entradas implements BaseColumns {
        public static final String NOMBRE_TABLA = "usuario";
        public static final String COL_EMAIL = "email";
        public static final String COL_PASSWORD = "password";
    }

}
