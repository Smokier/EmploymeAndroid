package com.example.employme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
    Intent intent;
    Bundle extras;
    String tipo=null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        intent = getIntent();
        extras=intent.getExtras();
        tipo=extras.getString("Tipo");


    }
}
