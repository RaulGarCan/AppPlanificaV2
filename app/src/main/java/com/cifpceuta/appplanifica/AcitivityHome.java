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
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class AcitivityHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acitivity_home);

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

        String usuario = preferencias.getString("Email","error");
        DefaultFragment defaultFragment = DefaultFragment.newInstance(usuario);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragments,defaultFragment).commit();
        }
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

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_fragments, new PerfilEstudianteFragment())
                    .commit();

        }
        else
        if (itemId == R.id.nav_logout) {
            // Acción para la opción 2
            // Puedes realizar una acción diferente aquí
            // Por ejemplo, iniciar una nueva actividad
            cerrarSesion();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}