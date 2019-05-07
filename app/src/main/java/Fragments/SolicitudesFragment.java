package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import Adapter.InteresadasAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.employme.Empresa;
import com.example.employme.R;

import java.util.ArrayList;
import java.util.List;

import Retrofit.RetrofitClient;
import retrofit2.Response;

public class SolicitudesFragment extends Fragment {

    RecyclerView recycler;
    Intent intent;
    Bundle extras;
    String tipo=null,nombre=null,mail=null,username=null,foto=null,contra=null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_solicitudes,container,false);
        intent = getActivity().getIntent();
        extras=intent.getExtras();

        tipo=extras.getString("Tipo");
        nombre=extras.getString("Nombre");
        mail=extras.getString("Email");
        username=extras.getString("Username");
        contra=extras.getString("Pass");
        foto=extras.getString("Foto");

        recycler=v.findViewById(R.id.interesadas);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        Call<List<Empresa>> call = RetrofitClient.getInstance().getApi().getInteresadass(extras.getString("Id"),"Android");

        call.enqueue(new Callback<List<Empresa>>() {
            @Override
            public void onResponse(Call<List<Empresa>> call, Response<List<Empresa>> response) {
                List<Empresa> emp = new ArrayList<>();
                List<Empresa> em = response.body();

                for (int i = 0; i<em.size();i++)
                {
                    emp.add(new Empresa(em.get(i).getNom_emp(),em.get(i).getEmail_emp(),em.get(i).getUsu_emp(),em.get(i).getFoto_emp(),em.get(i).getId_emp()));
                }

                Toast.makeText(getContext(),Integer.toString(emp.size()),Toast.LENGTH_SHORT).show();
                InteresadasAdapter adapter = new InteresadasAdapter(getContext(),emp);
                recycler.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Empresa>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

        return v;



    }
}
