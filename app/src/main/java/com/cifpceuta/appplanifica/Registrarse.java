package com.cifpceuta.appplanifica;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Registrarse extends AppCompatActivity {
    private Button btnIniciarSesion;
    private EditText etEmail, etPassword, etNombre;
    private Spinner spGrupo;
    private RadioButton rbTarde, rbManiana;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.toolbar_registro);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Grupos[] grupos = Grupos.values();

        String[] valoresGrupos = new String[Grupos.values().length];

        for(int i = 0; i < grupos.length; i++){
            valoresGrupos[i] = grupos[i].name();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, valoresGrupos);

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

        rbManiana.setChecked(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void registrarse(){
        String nombre = etNombre.getText().toString();
        String email = etEmail.getText().toString();
        String grupo = spGrupo.getSelectedItem().toString();
        String turno;
        if(rbTarde.isChecked()){
            turno = "Tarde";
        } else if(rbManiana.isChecked()){
            turno = "Mañana";
        } else {
            turno = "";
        }
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
                            Estudiante estudiante = new Estudiante(nombre,email,grupo,turno);
                            completarRegistroUsuario(estudiante,mAuth.getCurrentUser().getUid());


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
    public void completarRegistroUsuario(Estudiante estudiante, String userID){
        Map<String, Object> estudianteMap = new HashMap<>();
        estudianteMap.put("nombre", estudiante.getNombre());
        estudianteMap.put("email",estudiante.getEmail());
        estudianteMap.put("grupo", estudiante.getGrupo());
        estudianteMap.put("turno", estudiante.getTurno());

        db.collection("estudiantes").document(userID)
                .set(estudianteMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("RegistroCompletoUsuario", "Correcto");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("RegistroCompletoUsuario", "Fallido", e);
                    }
                });
    }
}