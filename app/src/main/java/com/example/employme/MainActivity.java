package com.example.employme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn1,btn2;
    Intent intent;
    String tipo=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion);
    }


    public void sendEmpresa(View view) {

        intent = new Intent(this,Activity.class);
        tipo="Empresa";
        intent.putExtra("Tipo",tipo);
        startActivity(intent);
    }


}
