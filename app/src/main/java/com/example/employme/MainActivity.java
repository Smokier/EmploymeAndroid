package com.example.employme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button btn1,btn2;
    Intent intent;
    String tipo=null;
Aspirante asp;
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
        finish();
    }

    public void sendAspirante (View view)
    {
        cargarSesion();
        intent = new Intent(this,Activity.class);
        tipo="Aspirante";
        intent.putExtra("Tipo",tipo);
        startActivity(intent);
        finish();
    }

    private void cargarSesion()
    {
        SharedPreferences data = getSharedPreferences("session",Context.MODE_PRIVATE);
        final String user=data.getString("usuario","No existe la info");
        String pass=data.getString("contra","No existe la info");
        if (user !="No existe la info" && pass!="No existe la info")
        {

            Call<Aspirante> call = RetrofitClient.getInstance().getApi().loginAsp(user,pass,"Android");

            call.enqueue(new Callback<Aspirante>() {
                @Override
                public void onResponse(Call<Aspirante> call, Response<Aspirante> response) {
                    asp = response.body();

                    if(asp.getEmail_asp().equals(user) || asp.getUsu_asp().equals(user))
                    {
                        intent = new Intent(getApplicationContext(),Menu.class);
                        intent.putExtra("Tipo",tipo);
                        intent.putExtra("Id",asp.getId_asp());
                        intent.putExtra("Nombre",asp.getNom_asp());
                        intent.putExtra("Email",asp.getEmail_asp());
                        intent.putExtra("Username",asp.getUsu_asp());
                        intent.putExtra("Pass",asp.getPsw_asp());
                        intent.putExtra("Foto",asp.getFoto_asp());
                        intent.putExtra("Tipo",tipo);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Aspirante> call, Throwable t) {

                }
            });

        }

    }
}
