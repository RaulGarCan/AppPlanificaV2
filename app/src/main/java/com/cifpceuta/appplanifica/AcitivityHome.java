package com.cifpceuta.appplanifica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AcitivityHome extends AppCompatActivity {
    private Button btnCerrarSesion;
    private TextView nombreUsuario;
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitivity_home);

        btnCerrarSesion = findViewById(R.id.btn_cerrar_sesion);
        nombreUsuario = findViewById(R.id.tv_nombre_usuario);

        preferencias = this.getSharedPreferences("Preferencias",MODE_PRIVATE);
        editor = preferencias.edit();

        String usuario = preferencias.getString("Email","error");
        nombreUsuario.setText(usuario);


        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferencias.edit().putString("Email","none").apply();
                startActivity(new Intent(AcitivityHome.this,MainActivity.class));
            }
        });
    }
}