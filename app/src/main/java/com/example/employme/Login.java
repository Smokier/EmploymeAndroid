package com.example.employme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import OpneHelper.SQLite_OpenHelper;
import Retrofit.INodeJS;
import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {
    Intent intent;
    Bundle extras;
    String tipo=null;
    SQLite_OpenHelper db = new SQLite_OpenHelper(this,"Employme",null,1);
    Cifrado c = new Cifrado();
    EditText username,pass;
    String claveEmp=null;
    SecretKey originalKey;

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


    public void loginAsp(View view) throws Exception {

        username=findViewById(R.id.user);
        pass=findViewById(R.id.psw);

        try {
            //Se realiza la petición al servidor para obtener los datos si es que el usuario ya está registrado
            Call<Aspirante> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    //Se ejecuta el metodo y se envían como parametros el correo o username y contraseña del usuario
                    .loginAsp(username.getText().toString(),pass.getText().toString(),"Android");

            call.enqueue(new Callback<Aspirante>() {
                @Override
                public void onResponse(Call<Aspirante> call, Response<Aspirante> response) {
                    Aspirante asp = response.body();

                    if(asp.getEmail_asp().equals(username.getText().toString()) || asp.getUsu_asp().equals(username.getText().toString()))
                    {
                        intent = new Intent(Login.this,Menu.class);
                        intent.putExtra("Tipo",tipo);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Aspirante> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void loginEmp(View v) throws Exception {

        username=findViewById(R.id.userCompany);
        pass=findViewById(R.id.passCompany);
        byte[] decodedKey = Base64.getDecoder().decode(claveEmp);
        originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        try {

            Cursor cursor =db.consultarEmpresas(username.getText().toString(),c.encriptar(pass.getText().toString(),originalKey));
            String nombre=null;
            if(cursor.getCount()>0)
            {

/*       //Comentado para verificar los datos obtenidos de la bd
                if (cursor.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        String id= cursor.getString(0);
                        nombre = cursor.getString(3);
                    } while(cursor.moveToNext());
                }*/
                Toast.makeText(getApplicationContext(),nombre,Toast.LENGTH_SHORT).show();

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
