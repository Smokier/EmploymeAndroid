package com.example.employme;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import OpneHelper.SQLite_OpenHelper;

public class Login extends AppCompatActivity {
    Intent intent;
    Bundle extras;
    String tipo=null;
    SQLite_OpenHelper db = new SQLite_OpenHelper(this,"Employme",null,1);

    EditText username,pass;

    protected void onCreate(Bundle savedInstanceState) {

        intent = getIntent();
        extras=intent.getExtras();
        tipo=extras.getString("Tipo");

        if(tipo.equals("Aspirante"))
        {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.login_asp);
        }
        else if (tipo.equals("Empresa"))
        {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.login_emp);
        }



    }
    public void sendSignUp(View view) {

        intent = new Intent(this,SignUp.class);
        intent.putExtra("Tipo",tipo);
        startActivity(intent);

    }


    public void loginAsp(View view) {

        username=findViewById(R.id.user);
        pass=findViewById(R.id.psw);

        try {
            Cursor cursor =db.consultarAspirante(username.getText().toString(),pass.getText().toString());
            if(cursor.getCount()>0)
            {

                //Comentado para verificar los datos obtenidos de la bd
               /* if (cursor.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        String id= cursor.getString(0);
                        nombre = cursor.getString(1);
                    } while(cursor.moveToNext());
                }
                Toast.makeText(getApplicationContext(),nombre,Toast.LENGTH_SHORT).show();*/

                intent = new Intent(this,Menu.class);
                intent.putExtra("Tipo",tipo);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"usuario y/o password incorrectos",Toast.LENGTH_SHORT).show();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void loginEmp(View v) {

        username=findViewById(R.id.userCompany);
        pass=findViewById(R.id.passCompany);

        try {
            Cursor cursor =db.consultarEmpresas(username.getText().toString(),pass.getText().toString());
            if(cursor.getCount()>0)
            {

                //Comentado para verificar los datos obtenidos de la bd
               /* if (cursor.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        String id= cursor.getString(0);
                        nombre = cursor.getString(1);
                    } while(cursor.moveToNext());
                }
                Toast.makeText(getApplicationContext(),nombre,Toast.LENGTH_SHORT).show();*/

                intent = new Intent(this,Menu.class);
                intent.putExtra("Tipo",tipo);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"usuario y/o password incorrectos",Toast.LENGTH_SHORT).show();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
}
