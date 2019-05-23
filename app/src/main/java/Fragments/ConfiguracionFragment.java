package Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employme.MainActivity;
import com.example.employme.R;

public class ConfiguracionFragment extends Fragment {
    Intent intent;
    Bundle extras;
    String tipo;
    TextView soporte;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Button cerrar;

        View v = inflater.inflate(R.layout.fragment_configuracion,container,false);
        intent = getActivity().getIntent();
        extras=intent.getExtras();
        tipo=extras.getString("Tipo");
        Toast.makeText(getContext(),extras.getString("Tipo"),Toast.LENGTH_SHORT).show();


        soporte=v.findViewById(R.id.soporte);

        cerrar=v.findViewById(R.id.cerrarSesion);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo.equals("Empresa"))
                {

                    SharedPreferences data = getActivity().getSharedPreferences("sessionEmp",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = data.edit();
                    editor.remove("usuarioEmp");
                    editor.remove("contraEmp");
                    editor.apply();

                    intent=new Intent(getContext(),MainActivity.class);
                    startActivity(intent);
                }
                else
                {

                    SharedPreferences data = getActivity().getSharedPreferences("session",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = data.edit();
                    editor.remove("usuario");
                    editor.remove("contra");
                    editor.apply();

                    intent=new Intent(getContext(),MainActivity.class);
                    startActivity(intent);
                }


            }
        });

        soporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSoporte(v);
            }
        });



        return v;

    }



    public void callSoporte(View v) {
        Intent i = new Intent (Intent.ACTION_CALL, Uri.parse("tel:5567056027"));
        if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)
            return;
        startActivity(i);
    }


}
