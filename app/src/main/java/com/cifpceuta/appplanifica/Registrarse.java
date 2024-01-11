package com.cifpceuta.appplanifica;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Registrarse extends AppCompatActivity {
    private Button btnIniciarSesion;
    private EditText etEmail, etPassword, etNombre;
    private Spinner spGrupo;
    private RadioButton rbTarde, rbManiana;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar_registro);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Grupos[] grupos = Grupos.values();

        String[] valoresGrupos = new String[Grupos.values().length];

        for(int i = 0; i < grupos.length; i++){
            valoresGrupos[i] = grupos[i].name();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, valoresGrupos);

        spGrupo = findViewById(R.id.sp_grupo_registrarse);
        spGrupo.setAdapter(adapter);

        btnIniciarSesion = findViewById(R.id.btn_registrarse);
        etEmail = findViewById(R.id.et_email_registrarse);
        etPassword = findViewById(R.id.et_password_registrarse);
        etNombre = findViewById(R.id.et_nombre_registrarse);
        rbManiana = findViewById(R.id.rb_maniana_registrarse);
        rbTarde = findViewById(R.id.rb_tarde_registrarse);
        spGrupo = findViewById(R.id.sp_grupo_registrarse);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(comprobarCampos()){
                    registrarse();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        //bd.close();
        super.onDestroy();
    }
    public void registrarse(){
        mAuth
                .createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Registro realizado correctamente!",
                                            Toast.LENGTH_LONG)
                                    .show();


                            // si el usuario se ha creado volvemos al Activity Principal para que se pueda logear
                            Intent intent
                                    = new Intent(Registrarse.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }
                        else {

                            // En este punto algo ha fallado, lo notificaremos
                            Toast.makeText(
                                            getApplicationContext(),
                                            "El registro ha fallado!!"
                                                    + " Intentelo mas tarde...",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
    }
    public boolean comprobarCampos(){
        return !etPassword.getText().toString().isEmpty() || !etEmail.getText().toString().isEmpty();
    }
    public void completarRegistroUsuario(){

    }
}