package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.employme.Aspirante;
import com.example.employme.Empresa;
import com.example.employme.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AspirantesAdapter extends RecyclerView.Adapter<AspirantesAdapter.ViewHolder> {

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

    public List<Aspirante> asps;
    Context context;

    public AspirantesAdapter (Context context, List<Aspirante> asps)
    {
        this.context=context;
        this.asps=asps;
    }

    @NonNull
    @Override
    public AspirantesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_solicitudes,viewGroup,false);
        AspirantesAdapter.ViewHolder viewHolder = new AspirantesAdapter.ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AspirantesAdapter.ViewHolder viewHolder, int i) {
        viewHolder.nomEmp.setText(asps.get(i).getNom_asp());
        viewHolder.emailEmp.setText(asps.get(i).getEmail_asp());
        viewHolder.tipoEmp.setText(asps.get(i).getFn_asp() +" Años");
        Picasso.with(context.getApplicationContext()).load("http://3.93.218.234/"+asps.get(i).getFoto_asp()).into(viewHolder.fotoEmp);

    }

    @Override
    public int getItemCount() {
        return asps.size() ;
    }
}
