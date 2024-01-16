package com.cifpceuta.appplanifica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnSiguiente;
    private TextView tvTexto;
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;
    private Button btnIniciarSesion, btnRegistrarse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferencias = this.getSharedPreferences("Preferencias",MODE_PRIVATE);
        editor = preferencias.edit();

        btnSiguiente = findViewById(R.id.ib_siguiente);
        tvTexto = findViewById(R.id.tv_texto);
        btnIniciarSesion = findViewById(R.id.btn_activity_iniciar_sesion);
        btnRegistrarse = findViewById(R.id.btn_activity_registrarse);

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

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IniciarSesion.class);
                startActivity(intent);
            }
        });
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registrarse.class);
                startActivity(intent);
            }
        });
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
        tvTexto.setVisibility(View.GONE);
        btnSiguiente.setVisibility(View.GONE);
        editor.putBoolean("Bienvenida",false).apply();
        btnIniciarSesion.setVisibility(View.VISIBLE);
        btnRegistrarse.setVisibility(View.VISIBLE);
        String email = preferencias.getString("Email","none");
        if(!email.equalsIgnoreCase("none")){
            startActivity(new Intent(MainActivity.this, AcitivityHome.class));
        }
    }
}