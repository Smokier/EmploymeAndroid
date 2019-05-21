package com.example.employme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    Button loginA,loginE;
    Intent intent;
    String tipo=null;
    Aspirante asp;
    Empresa emp;
    boolean tipoConexion1 = false;
    boolean tipoConexion2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion);
        internetConnection();
    }


    public void sendEmpresa(View view) {
        cargarSesionEmp();
        intent = new Intent(this,Activity.class);
        tipo="Empresa";
        intent.putExtra("Tipo",tipo);
        startActivity(intent);
    }

    public void sendAspirante (View view)
    {
        cargarSesionAsp();
        intent = new Intent(this,Activity.class);
        tipo="Aspirante";
        intent.putExtra("Tipo",tipo);
        startActivity(intent);
    }

    private void cargarSesionAsp()
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
                        intent.putExtra("UserGit",asp.getUsugh_pasp());
                        intent.putExtra("Video",asp.getVyt_pasp());
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


    public void cargarSesionEmp()
    {
        SharedPreferences data = getSharedPreferences("sessionEmp",Context.MODE_PRIVATE);
        final String user=data.getString("usuarioEmp","No existe la info");
        String pass=data.getString("contraEmp","No existe la info");

        if (user !="No existe la info" && pass!="No existe la info") {

            Call<Empresa> call = RetrofitClient.getInstance().getApi().loginEmp(user, pass, "Android");

            call.enqueue(new Callback<Empresa>() {
                @Override
                public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                    emp = response.body();
                    if(emp.getEmail_emp().equals(user) || emp.getUsu_emp().equals(user))
                    {
                        intent = new Intent(getApplicationContext(),MenuEmpresa.class);
                        intent.putExtra("Tipo",tipo);
                        intent.putExtra("Id_Emp",emp.getId_emp());
                        intent.putExtra("Nombre",emp.getNom_emp());
                        intent.putExtra("Email",emp.getEmail_emp());
                        intent.putExtra("Username",emp.getUsu_emp());
                        intent.putExtra("Pass",emp.getPsw_emp());
                        intent.putExtra("Foto",emp.getFoto_emp());
                        intent.putExtra("Tipo",tipo);
                        startActivity(intent);
                    }

                }

                @Override
                public void onFailure(Call<Empresa> call, Throwable t) {

                }
            });

        }

    }

public void internetConnection()
{
    ConnectivityManager cm;
    NetworkInfo ni;
    cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
    ni = cm.getActiveNetworkInfo();


    if (ni != null) {

        ConnectivityManager connManager1 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager1.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        ConnectivityManager connManager2 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mMobile = connManager2.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mWifi.isConnected()) {

            //Wifi
            tipoConexion1 = true;
        }
        if (mMobile.isConnected()) {

            //Datos
            tipoConexion2 = true;
        }

        if (tipoConexion1 == true || tipoConexion2 == true) {
        }
    }
    else {
        Toast.makeText(this, "No tienes conexión a internet", Toast.LENGTH_LONG).show();
        loginA = findViewById(R.id.loginAsp);
        loginE = findViewById(R.id.loginEmp);

        loginA.setEnabled(false);
        loginE.setEnabled(false);

    }
}

}
