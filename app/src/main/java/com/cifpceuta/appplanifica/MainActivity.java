package com.cifpceuta.appplanifica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ManejadorSQLite manejador;
    SQLiteDatabase bd;
    private ImageButton btnSiguiente;
    private TextView tvTexto;
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferencias = this.getSharedPreferences("Preferencias",MODE_PRIVATE);
        editor = preferencias.edit();

        btnSiguiente = findViewById(R.id.ib_siguiente);
        tvTexto = findViewById(R.id.tv_texto);

        boolean bienvenida = preferencias.getBoolean("Bienvenida",true);

        if(!bienvenida){
            pantallaPrincipal();
        }
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pantalla2();
            }
        });

        manejador = new ManejadorSQLite(this);
        bd = manejador.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(DefinicionBD.Entradas.COL_EMAIL, "email1@gmail.com");
        valores.put(DefinicionBD.Entradas.COL_PASSWORD, "contraseña1");

    }

    @Override
    protected void onDestroy() {
        bd.close();
        super.onDestroy();
    }
    public void insertar(ContentValues valores){
        bd.insert(DefinicionBD.Entradas.NOMBRE_TABLA, null, valores);
    }
    public void pantalla2(){
        tvTexto.setText("Texto2");
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pantalla3();
            }
        });
    }
    public void pantalla3(){
        tvTexto.setText("Texto3");
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pantallaPrincipal();
            }
        });
    }
    public void pantallaPrincipal(){
        tvTexto.setText("Home");
        btnSiguiente.setVisibility(View.GONE);
        editor.putBoolean("Bienvenida",false).apply();

    }
}