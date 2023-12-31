package com.cifpceuta.appplanifica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class IniciarSesion extends AppCompatActivity {
    private Button btnIniciarSesion;
    private EditText etEmail, etPassword;
    private SharedPreferences preferences;
    private ImageButton btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        btnIniciarSesion = findViewById(R.id.btn_iniciar_sesion);
        etEmail = findViewById(R.id.et_email_iniciar_sesion);
        etPassword = findViewById(R.id.et_password_iniciar_sesion);
        btnHome = findViewById(R.id.btn_home_iniciar_sesion);

        preferences = this.getSharedPreferences("Preferencias",MODE_PRIVATE);

        //manejador = new ManejadorSQLite(this);
        //bd = manejador.getWritableDatabase();

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobarCampos()){
                    //iniciarSesion();
                    if(byPassTmp()){
                        preferences.edit().putString("Email","Usuario").apply();
                        startActivity(new Intent(IniciarSesion.this, MainActivity.class));
                    }
                }
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IniciarSesion.this, MainActivity.class));
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
    public boolean iniciarSesion(){
        String selection = DefinicionBD.Entradas.COL_EMAIL + " = ?";
        String[] selectionArgs = {etEmail.getText().toString()};
        /*
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
                preferences.edit().putString("Email",etEmail.getText().toString()).apply();
                startActivity(new Intent(IniciarSesion.this, MainActivity.class));
                return true;
            }
        }
         */
        return false;
    }
    public boolean comprobarCampos(){
        return !etPassword.getText().toString().isEmpty() || !etEmail.getText().toString().isEmpty();
    }
    public boolean byPassTmp(){
        return etEmail.getText().toString().equalsIgnoreCase("a") && etPassword.getText().toString().equalsIgnoreCase("a");
    }
}