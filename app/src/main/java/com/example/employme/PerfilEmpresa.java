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

import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilEmpresa extends AppCompatActivity {
    Intent intent;
    Bundle extras;
    String nombre,correo,foto;
    Button btn;
    ImageView pic;
    EditText nom,des;
    EditText c;
    LinearLayout p;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_perfil_emp);


        intent = getIntent();
        extras=intent.getExtras();
        nombre=extras.getString("Nombre");
        correo=extras.getString("Email");
        foto=extras.getString("Foto");

        pic = findViewById(R.id.foto_perfil);
        nom= findViewById(R.id.name);
        c=findViewById(R.id.mail);
        des=findViewById(R.id.descripcion);


        c.setText(correo);
        nom.setText(nombre);
        nom.setEnabled(false);
        c.setEnabled(false);
        Picasso.with(getApplicationContext()).load("http://3.93.218.234/"+foto).error(R.drawable.person_icon).into(pic);

        Call<Empresa> call2= RetrofitClient.getInstance().getApi().getDataEmp(extras.getString("Id"),"Android");

        call2.enqueue(new Callback<Empresa>() {
            @Override
            public void onResponse(Call<Empresa> call, Response<Empresa> response) {

                Empresa emp = response.body();
                Toast.makeText(getApplicationContext(),response.body().getDes_emp(),Toast.LENGTH_SHORT).show();
                des.setText(emp.getDes_emp());

            }

            @Override
            public void onFailure(Call<Empresa> call, Throwable t) {
                Toast.makeText(PerfilEmpresa.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });




    }

}
