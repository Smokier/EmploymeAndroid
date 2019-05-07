package com.example.employme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import Fragments.ConfiguracionFragment;
import Fragments.AspirantesFragment;
import Fragments.PerfilEmpresaFragment;

public class MenuEmpresa extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_emp );

        BottomNavigationView bottonNav = findViewById(R.id.menu_navegacion);
        bottonNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PerfilEmpresaFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId())
            {
                case R.id.perfil: selectedFragment = new PerfilEmpresaFragment();
                    break;
                case R.id.opciones: selectedFragment = new ConfiguracionFragment();
                    break;
                case R.id.soli: selectedFragment = new AspirantesFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

            return true;
        }
    };


}