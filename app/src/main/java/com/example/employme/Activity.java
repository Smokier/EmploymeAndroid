package com.example.employme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Activity extends AppCompatActivity {
    Intent intent;
    Bundle extras;
    String tipo=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        extras=intent.getExtras();
        tipo=extras.getString("Tipo");

    }

    public void sendLogin(View view) {

        intent = new Intent(this,Login.class);
        intent.putExtra("Tipo",tipo);
        startActivity(intent);
        finish();

    }

    public void sendSignUp(View view) {

        intent = new Intent(this,SignUp.class);
        intent.putExtra("Tipo",tipo);
        startActivity(intent);
        finish();
    }
}
