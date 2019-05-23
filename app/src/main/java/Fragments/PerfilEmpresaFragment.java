package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employme.Empresa;
import com.example.employme.R;
import com.squareup.picasso.Picasso;

import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilEmpresaFragment extends Fragment {

    RecyclerView recycler;
    Intent intent;
    TextView name,des,email;
    ImageView imageView;
    LinearLayout datos;
    Bundle extras;
    String tipo=null,nombre=null,mail=null,username=null,foto=null,contra=null,id=null;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perfil_emp, container, false);

        intent = getActivity().getIntent();
        extras=intent.getExtras();

        id=extras.getString("Id_Emp");
        nombre = extras.getString("Nombre");
        tipo=extras.getString("Tipo");
        mail=extras.getString("Email");
        username=extras.getString("Username");
        contra=extras.getString("Pass");
        foto=extras.getString("Foto");

        imageView=v.findViewById(R.id.foto_perfil);

        name=v.findViewById(R.id.name);
        name.setText(nombre);


        email=v.findViewById(R.id.mail);
        email.setText(mail);

        des=v.findViewById(R.id.descripcion);


        //Foto de la empresa
        Call<Empresa> call = RetrofitClient.getInstance().getApi().getFotoEmp(id,"Android");

        call.enqueue(new Callback<Empresa>() {
            @Override
            public void onResponse(Call<Empresa> call, Response<Empresa> response) {
                Log.d("FotoRuta",response.body().getFoto_emp());
                Picasso.with(getContext()).load("http://3.93.218.234/"+response.body().getFoto_emp()).error(R.drawable.person_icon).into(imageView);


            }

            @Override
            public void onFailure(Call<Empresa> call, Throwable t) {

                Toast.makeText(getActivity().getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT);
            }
        });

        Call<Empresa> call2= RetrofitClient.getInstance().getApi().getDataEmp(id,"Android");

        call2.enqueue(new Callback<Empresa>() {
            @Override
            public void onResponse(Call<Empresa> call, Response<Empresa> response) {

                Empresa emp = response.body();
                des.setText(emp.getDes_emp());

            }

            @Override
            public void onFailure(Call<Empresa> call, Throwable t) {

            }
        });







        return v;
    }
}
