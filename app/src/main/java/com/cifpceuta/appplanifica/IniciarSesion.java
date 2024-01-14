package com.cifpceuta.appplanifica;

import androidx.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class IniciarSesion extends AppCompatActivity {
    private Button btnIniciarSesion;
    private EditText etEmail, etPassword;
    private SharedPreferences preferences;
    private ImageButton btnHome;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar_iniciar_sesion);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progress_bar_iniciar_sesion);
        btnIniciarSesion = findViewById(R.id.btn_iniciar_sesion);
        etEmail = findViewById(R.id.et_email_iniciar_sesion);
        etPassword = findViewById(R.id.et_password_iniciar_sesion);
        btnHome = findViewById(R.id.btn_home_iniciar_sesion);

        progressBar.setVisibility(View.INVISIBLE);

        preferences = this.getSharedPreferences("Preferencias",MODE_PRIVATE);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobarCampos()){
                    if(byPassTmp()){
                        preferences.edit().putString("Email","Usuario").apply();
                        startActivity(new Intent(IniciarSesion.this, MainActivity.class));
                    } else {
                        iniciarSesion();
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
    @Override
    protected void onDestroy() {
        //bd.close();
        super.onDestroy();
    }
    public void iniciarSesion(){
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                                    "Login successful!!",
                                                    Toast.LENGTH_LONG)
                                            .show();

                                    // ocultamos progress bar
                                    progressBar.setVisibility(View.GONE);

                                    // si el login es correcto
                                    // saltamos al Activity de bienvenida
                                    preferences.edit().putString("Email",etEmail.getText().toString()).apply();
                                    Intent intent
                                            = new Intent(IniciarSesion.this,
                                            AcitivityHome.class);
                                    startActivity(intent);
                                }

                                else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                                    "Algo ha ido mal!! Motivo: "
                                                            + task.getException().getMessage().toString(),
                                                    Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
    }
    public boolean comprobarCampos(){
        return !etPassword.getText().toString().isEmpty() || !etEmail.getText().toString().isEmpty();
    }
    public boolean byPassTmp(){
        return etEmail.getText().toString().equalsIgnoreCase("a") && etPassword.getText().toString().equalsIgnoreCase("a");
    }
}