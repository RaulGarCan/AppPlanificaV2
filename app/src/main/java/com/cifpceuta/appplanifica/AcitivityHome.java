package com.cifpceuta.appplanifica;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AcitivityHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private String nombre, correo, turno, grupo;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitivity_home);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        preferencias = this.getSharedPreferences("Preferencias",MODE_PRIVATE);
        editor = preferencias.edit();

        toolbar = findViewById(R.id.toolbar);
        // activamos el Menu Desplegable Lateral
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        correo = preferencias.getString("Email","error");

        DefaultFragment defaultFragment = DefaultFragment.newInstance(correo);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragments,defaultFragment).commit();
        }

        obtenerDatosUsuario();

        addModulosDB();
    }
    public void cerrarSesion(){
        preferencias.edit().putString("Email","none").apply();
        startActivity(new Intent(AcitivityHome.this,MainActivity.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_account) {
            // Acción para la opción 1
            // Puedes abrir un nuevo fragmento, iniciar una nueva actividad, etc.

            PerfilEstudianteFragment perfilEstudianteFragment = PerfilEstudianteFragment.newInstance(nombre, correo, turno, grupo);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_fragments, perfilEstudianteFragment)
                    .commit();

        }
        else
        if (itemId == R.id.nav_logout) {
            // Acción para la opción 2
            // Puedes realizar una acción diferente aquí
            // Por ejemplo, iniciar una nueva actividad
            cerrarSesion();
        }
        else
        if (itemId == R.id.plan_practica){
            PlanificarPracticaFragment planificarPracticaFragment = new PlanificarPracticaFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_fragments,planificarPracticaFragment)
                    .commit();
        }
        else
        if (itemId == R.id.list_practicas){
            ListarPracticasFragment listarPracticasFragment = new ListarPracticasFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_fragments,listarPracticasFragment)
                    .commit();
        }
        else
        if (itemId == R.id.list_practicas_semanal){
            ListarPracticasSemanalFragment listarPracticasSemanalFragment = new ListarPracticasSemanalFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_fragments,listarPracticasSemanalFragment)
                    .commit();
        }
        else
        if (itemId == R.id.crear_actividad_extra){
            ActividadesExtraFragment actividadesExtraFragment = new ActividadesExtraFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_fragments,actividadesExtraFragment)
                    .commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void obtenerDatosUsuario(){
        String userId = auth.getCurrentUser().getUid();
        db.collection("estudiantes").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        Map<String, Object> datos = document.getData();
                        nombre = (String)datos.get("nombre");
                        turno = (String)datos.get("turno");
                        grupo = (String)datos.get("grupo");
                    }else {
                        Log.w("ConseguirDatosUser","Documento no encontrado");
                    }
                } else {
                    Log.w("ConseguirDatosUser","Error: "+task.getException());
                }
            }
        });
    }
    public void addModulosDB(){
        Map<String,Object> modulos = new HashMap<>();
        ArrayList<String> listaModulos = new ArrayList<>();
        listaModulos.add("SGE");
        listaModulos.add("AD");
        listaModulos.add("PSYP");
        listaModulos.add("DI");
        listaModulos.add("PMDM");
        listaModulos.add("EIE");
        String clave = "DAM2";
        modulos.put(clave,listaModulos);
        db.collection("modulos").document(clave).set(modulos);
    }
}