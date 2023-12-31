package com.cifpceuta.appplanifica;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Registrarse extends AppCompatActivity {
    private Button btnIniciarSesion;
    private EditText etEmail, etPassword, etNombre, etApellido, etCurso;
    private ImageButton btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        btnIniciarSesion = findViewById(R.id.btn_registrarse);
        etEmail = findViewById(R.id.et_email_registrarse);
        etPassword = findViewById(R.id.et_password_registrarse);
        etNombre = findViewById(R.id.et_nombre_registrarse);
        etApellido = findViewById(R.id.et_apellido_registrarse);
        etCurso = findViewById(R.id.et_curso_registrarse);
        btnHome = findViewById(R.id.btn_home_registrarse);

        //manejador = new ManejadorSQLite(this);
        //bd = manejador.getWritableDatabase();

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobarCampos()){
                    registrarse();
                }
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registrarse.this, MainActivity.class));
            }
        });
    }
    public void insertar(ContentValues valores){
        //bd.insert(DefinicionBD.Entradas.NOMBRE_TABLA, null, valores);
    }
    @Override
    protected void onDestroy() {
        //bd.close();
        super.onDestroy();
    }
    public void registrarse(){
        ContentValues valores = new ContentValues();
        valores.put(DefinicionBD.Entradas.COL_EMAIL, etEmail.getText().toString());
        valores.put(DefinicionBD.Entradas.COL_PASSWORD,etPassword.getText().toString());
        // Add valores de et_nombre, et_apellido y et_curso
        insertar(valores);
        startActivity(new Intent(this, MainActivity.class));
    }
    public boolean comprobarCampos(){
        return !etPassword.getText().toString().isEmpty() || !etEmail.getText().toString().isEmpty();
    }
}