package Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.employme.Aspirante;
import com.example.employme.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.AspirantesAdapter;
import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AspirantesFragment extends Fragment {

    RecyclerView recycler;
    Intent intent;
    Bundle extras;
    String tipo;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_solicitudes, container, false);
        intent = getActivity().getIntent();
        extras=intent.getExtras();

        recycler=v.findViewById(R.id.interesadas);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        tipo=extras.getString("Tipo");
        Call<List<Aspirante>> call = RetrofitClient.getInstance().getApi().getAspirantes("Android");

        call.enqueue(new Callback<List<Aspirante>>() {
            @Override
            public void onResponse(Call<List<Aspirante>> call, Response<List<Aspirante>> response) {
                List<Aspirante> asp = new ArrayList<>();
                List<Aspirante> em = response.body();

                for (int i = 0; i<em.size();i++)
                {
                    asp.add(new Aspirante(em.get(i).getNom_asp(),em.get(i).getEmail_asp(),em.get(i).getFn_asp(),em.get(i).getFoto_asp(),em.get(i).getId_asp()));
                }

                Toast.makeText(getContext(),Integer.toString(asp.size()),Toast.LENGTH_SHORT).show();
                AspirantesAdapter adapter = new AspirantesAdapter(getContext(),asp);
                recycler.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Aspirante>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });



    return v;
    }

}
