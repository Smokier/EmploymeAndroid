package com.example.employme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class PerfilEmpresa extends AppCompatActivity {
    Intent intent;
    Bundle extras;
    String nombre,correo,foto;
    Button btn;
    ImageView pic;
    EditText nom;
    EditText c;
    LinearLayout p;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_perfil);


        intent = getIntent();
        extras=intent.getExtras();
        nombre=extras.getString("Nombre");
        correo=extras.getString("Email");
        foto=extras.getString("Foto");

        pic = findViewById(R.id.foto_perfil);
        nom= findViewById(R.id.name);
        c=findViewById(R.id.mail);
        p=findViewById(R.id.pass);
        btn = findViewById(R.id.cambios);


        c.setText(correo);
        nom.setText(nombre);
        nom.setEnabled(false);
        btn.setVisibility(View.INVISIBLE);
        c.setEnabled(false);
        p.setVisibility(View.INVISIBLE);
        Picasso.with(getApplicationContext()).load("http://3.93.218.234/"+foto).error(R.drawable.person_icon).into(pic);




    }

}
