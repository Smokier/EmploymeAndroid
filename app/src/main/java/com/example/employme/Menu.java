package com.example.employme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import Fragments.ConfiguracionFragment;
import Fragments.PerfilFragment;
import Fragments.SolicitudesFragment;

public class Menu extends AppCompatActivity {
    Intent intent;
    Bundle extras;
    String tipo=null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        BottomNavigationView bottonNav = findViewById(R.id.menu_navegacion);
        bottonNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PerfilFragment()).commit();
        intent = getIntent();
        extras=intent.getExtras();
        tipo=extras.getString("Tipo");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId())
            {
                case R.id.perfil: selectedFragment = new PerfilFragment();
                break;
                case R.id.opciones: selectedFragment = new ConfiguracionFragment();
                    break;
                case R.id.soli: selectedFragment = new SolicitudesFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };
}
