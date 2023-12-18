package com.cifpceuta.appplanifica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class IniciarSesion extends AppCompatActivity {
    private Button btnIniciarSesion;
    private EditText etEmail, etPassword;
    ManejadorSQLite manejador;
    SQLiteDatabase bd;
    boolean registrarse = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        btnIniciarSesion = findViewById(R.id.btn_iniciar_sesion);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        manejador = new ManejadorSQLite(this);
        bd = manejador.getWritableDatabase();

        Intent intent = this.getIntent();
        if(intent!=null){
            registrarse = intent.getBooleanExtra("Registrarse",false);
        }

        if(registrarse){
            btnIniciarSesion.setText("Registrarse");
        }

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobarCampos()){
                    if(registrarse){
                        registrarse();
                    } else {
                        iniciarSesion();
                    }
                }
            }
        });
    }
    public void insertar(ContentValues valores){
        bd.insert(DefinicionBD.Entradas.NOMBRE_TABLA, null, valores);
    }
    @Override
    protected void onDestroy() {
        bd.close();
        super.onDestroy();
    }
    public void registrarse(){
        ContentValues valores = new ContentValues();
        valores.put(DefinicionBD.Entradas.COL_EMAIL, etEmail.getText().toString());
        valores.put(DefinicionBD.Entradas.COL_PASSWORD,etPassword.getText().toString());
        insertar(valores);
        startActivity(new Intent(this, MainActivity.class));
    }
    public boolean iniciarSesion(){
        String selection = DefinicionBD.Entradas.COL_EMAIL + " = ?";
        String[] selectionArgs = {etEmail.getText().toString()};

        Cursor cursor = bd.query(
                DefinicionBD.Entradas.NOMBRE_TABLA,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        ArrayList<String> passwords = new ArrayList<>();
        while(cursor.moveToNext()){
            String password = cursor.getString(cursor.getColumnIndexOrThrow(DefinicionBD.Entradas.COL_PASSWORD));
            passwords.add(password);
        }
        for(String s : passwords){
            if(s.equalsIgnoreCase(etPassword.getText().toString())){
                Intent intent = new Intent(IniciarSesion.this, MainActivity.class);
                intent.putExtra("Email",etEmail.getText().toString());
                startActivity(intent);
                return true;
            }
        }
        return false;
    }
    public boolean comprobarCampos(){
        return !etPassword.getText().toString().isEmpty() || !etEmail.getText().toString().isEmpty();
    }
}