package com.example.employme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    Intent intent;
    Bundle extras;
    String tipo=null;

    EditText ed1,ed2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_asp);


        intent = getIntent();
        extras=intent.getExtras();
        tipo=extras.getString("Tipo");


    }


}
