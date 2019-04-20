package com.example.employme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import OpneHelper.SQLite_OpenHelper;

public class SignUp extends AppCompatActivity {

    EditText nom,pass,confpass,mail;
    Intent intent;
    Bundle extras;
    String tipo=null;
    SQLite_OpenHelper db = new SQLite_OpenHelper(this,"Employme",null,1);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campos_reg);

        intent = getIntent();
        extras=intent.getExtras();
        tipo=extras.getString("Tipo");
        Toast.makeText(getApplicationContext(),tipo,Toast.LENGTH_SHORT).show();
    }

    public void regresar(View view) {
        intent = new Intent(this,Activity.class);
        intent.putExtra("Tipo",tipo);
        startActivity(intent);
    }

    public void signUp(View view){

        Aspirante asp = new Aspirante();

        nom=findViewById(R.id.name);
        pass=findViewById(R.id.password);
        confpass=findViewById(R.id.confirmPass);
        mail=findViewById(R.id.email);

        if((pass.getText().toString()).equals(confpass.getText().toString()))
        {
            asp.setNom_asp(nom.getText().toString());
            asp.setEmail_asp(mail.getText().toString());
            asp.setPsw_asp(pass.getText().toString());


            db.abrir();
            db.insertarAsp(asp);
            db.cerrar();

            Toast.makeText(getApplicationContext(),"Usuario Guardado",Toast.LENGTH_SHORT).show();

            intent = new Intent(this,Login.class);
            tipo="Aspirante";
            intent.putExtra("Tipo",tipo);
            startActivity(intent);

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
        }



    }



}
