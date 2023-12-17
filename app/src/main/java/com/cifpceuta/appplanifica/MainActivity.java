package com.cifpceuta.appplanifica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    ManejadorSQLite manejador;
    SQLiteDatabase bd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manejador = new ManejadorSQLite(this);
        bd = manejador.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(DefinicionBD.Entradas.COL_EMAIL, "email1@gmail.com");
        valores.put(DefinicionBD.Entradas.COL_PASSWORD, "contraseña1");

        bd.insert(DefinicionBD.Entradas.NOMBRE_TABLA, null, valores);
    }

    @Override
    protected void onDestroy() {
        bd.close();
        super.onDestroy();
    }
}