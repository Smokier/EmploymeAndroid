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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
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
    EditText username,pass;
    RadioButton radioButton;
    Empresa emp;
    Aspirante asp;
    private boolean isActive;
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

        radioButton=findViewById(R.id.RBsession);
        isActive=radioButton.isChecked();

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActive)
                {
                    radioButton.setChecked(false);
                }
                isActive=radioButton.isChecked();
            }
        });

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
                    asp = response.body();

                    if(asp.getEmail_asp().equals(username.getText().toString()) || asp.getUsu_asp().equals(username.getText().toString()))
                    {
                        if(isActive==true)
                        {
                            SharedPreferences preferences = getSharedPreferences("session", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("usuario", asp.getUsu_asp());
                            editor.putString("contra",asp.getPsw_asp());
                            editor.commit();
                        }
                        intent = new Intent(Login.this,Menu.class);
                        intent.putExtra("Tipo",tipo);
                        intent.putExtra("Id",asp.getId_asp());
                        intent.putExtra("Nombre",asp.getNom_asp());
                        intent.putExtra("Email",asp.getEmail_asp());
                        intent.putExtra("Username",asp.getUsu_asp());
                        intent.putExtra("Pass",asp.getPsw_asp());
                        intent.putExtra("Foto",asp.getFoto_asp());
                        intent.putExtra("Tipo",tipo);
                        startActivity(intent);
                        finish();
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
        try {

        //Se realiza la petición al servidor para obtener los datos si es que el usuario ya está registrado
            Call<Empresa> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    //Se ejecuta el metodo y se envían como parametros el correo o username y contraseña del usuario
                    .loginEmp(username.getText().toString(),pass.getText().toString(),"Android");

            call.enqueue(new Callback<Empresa>() {
                @Override
                public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                    emp =response.body();
                    if(emp.getEmail_emp().equals(username.getText().toString()) || emp.getUsu_emp().equals(username.getText().toString()))
                    {
                        intent = new Intent(Login.this,Menu.class);
                        intent.putExtra("Nombre",emp.getNom_emp());
                        intent.putExtra("Email",emp.getEmail_emp());
                        intent.putExtra("Username",emp.getUsu_emp());
                        intent.putExtra("Tipo",tipo);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Empresa> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

}
