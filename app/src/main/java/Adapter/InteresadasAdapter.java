package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.employme.Empresa;
import com.example.employme.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InteresadasAdapter extends RecyclerView.Adapter<InteresadasAdapter.ViewHolder> {



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nomEmp,emailEmp,tipoEmp;
        ImageView fotoEmp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomEmp=itemView.findViewById(R.id.nomEmp);
            emailEmp=itemView.findViewById(R.id.emailEmp);
            tipoEmp=itemView.findViewById(R.id.tipoEmp);
            fotoEmp=itemView.findViewById(R.id.fotoEmp);
        }
    }

    public List<Empresa> emps;
    Context context;
    public InteresadasAdapter (Context context, List<Empresa> emps)
    {
        this.context=context;
        this.emps=emps;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater .from(viewGroup.getContext()).inflate(R.layout.card_solicitudes,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InteresadasAdapter.ViewHolder viewHolder, int i) {
        viewHolder.nomEmp.setText(emps.get(i).getNom_emp());
        viewHolder.emailEmp.setText(emps.get(i).getNom_emp());
        Picasso.with(context.getApplicationContext()).load("http://3.93.218.234/"+emps.get(i).getFoto_emp()).into(viewHolder.fotoEmp);
    }

    @Override
    public int getItemCount() {
        return emps.size();
    }


/*
        public void asignarDatos(Empresa empresa) {
            nomEmp.setText(empresa.getNom_emp());
            emailEmp.setText(empresa.getEmail_emp());
            tipoEmp.setText(empresa.getUsu_emp());

        }
    }*/
}
