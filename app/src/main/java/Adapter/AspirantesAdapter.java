package Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employme.Aspirante;
import com.example.employme.Empresa;
import com.example.employme.PerfilAspirante;
import com.example.employme.R;
import com.squareup.picasso.Picasso;

import java.util.List;

class AspirantesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
{
    TextView nomEmp,emailEmp,tipoEmp,tipo,e;

    ImageView fotoEmp;
    public CardView card;
    private ItemClickListener itemClickListener;

    public AspirantesViewHolder(@NonNull View itemView) {
        super(itemView);
        card = itemView.findViewById(R.id.card);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        nomEmp=itemView.findViewById(R.id.nomEmp);
        emailEmp=itemView.findViewById(R.id.emailEmp);
        tipoEmp=itemView.findViewById(R.id.tipoEmp);
        fotoEmp=itemView.findViewById(R.id.fotoEmp);
        e=itemView.findViewById(R.id.Emps);
        tipo=itemView.findViewById(R.id.Asps);
        e.setVisibility(View.INVISIBLE);


    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),true);
        return true;
    }
}

public class AspirantesAdapter extends RecyclerView.Adapter<AspirantesViewHolder> {

    public List<Aspirante> asps;
    Context context;
    Intent intent;


    public AspirantesAdapter (Context context, List<Aspirante> asps)
    {
        this.context=context;
        this.asps=asps;
    }

    @NonNull
    @Override
    public AspirantesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_solicitudes,viewGroup,false);
        AspirantesViewHolder viewHolder = new AspirantesViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AspirantesViewHolder viewHolder, final int i) {

        viewHolder.nomEmp.setText(asps.get(i).getNom_asp());
        viewHolder.emailEmp.setText(asps.get(i).getEmail_asp());
        viewHolder.tipoEmp.setText(asps.get(i).getFn_asp() +" Años");
        Picasso.with(context.getApplicationContext()).load("http://3.93.218.234/"+asps.get(i).getFoto_asp()).error(R.drawable.person_icon).into(viewHolder.fotoEmp);
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                {

                    intent = new Intent(context,PerfilAspirante.class);
                    intent.putExtra("Nombre",asps.get(i).getNom_asp());
                    intent.putExtra("Email",asps.get(i).getEmail_asp());
                    intent.putExtra("Foto",asps.get(i).getFoto_asp());
                    intent.putExtra("Video",asps.get(i).getVyt_pasp());
                    context.startActivity(intent);
                }
                else
                {
                    intent = new Intent(context,PerfilAspirante.class);
                    intent.putExtra("Nombre",asps.get(i).getNom_asp());
                    intent.putExtra("Email",asps.get(i).getEmail_asp());
                    intent.putExtra("Foto",asps.get(i).getFoto_asp());
                    intent.putExtra("Id",asps.get(i).getId_asp());
                    intent.putExtra("Video",asps.get(i).getVyt_pasp());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return asps.size() ;
    }
}
