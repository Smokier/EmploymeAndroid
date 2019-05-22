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

import com.example.employme.R;

public class PerfilEmpresaFragment extends Fragment {

    RecyclerView recycler;
    Intent intent;
    Bundle extras;
    String tipo=null,nombre=null,mail=null,username=null,foto=null,contra=null;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perfil_emp, container, false);

        return v;
    }
}
