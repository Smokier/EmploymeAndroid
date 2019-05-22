package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.employme.R;
import com.squareup.picasso.Picasso;

import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilEmpresaFragment extends Fragment {

    RecyclerView recycler;
    Intent intent;
    TextView name,pass,email;
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


        //Foto de la empresa
        Call<String> call = RetrofitClient.getInstance().getApi().getFotoEmp(id,"Android");

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Picasso.with(getContext()).load("http://34.227.162.181/"+response.body()).error(R.drawable.person_icon) .into(imageView);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });







        return v;
    }
}
