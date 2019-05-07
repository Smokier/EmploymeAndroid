package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employme.Empresa;
import com.example.employme.R;
import com.squareup.picasso.Picasso;

import java.util.List;

class EmpresasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener
{
    TextView nomEmp,emailEmp,tipoEmp,idUser;
    ImageView fotoEmp;
    public CardView card;
    private ItemClickListener itemClickListener;

    public EmpresasViewHolder(@NonNull View itemView) {
        super(itemView);
        card = itemView.findViewById(R.id.card);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        nomEmp=itemView.findViewById(R.id.nomEmp);

        emailEmp=itemView.findViewById(R.id.emailEmp);
        tipoEmp=itemView.findViewById(R.id.tipoEmp);
        fotoEmp=itemView.findViewById(R.id.fotoEmp);
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


public class InteresadasAdapter extends RecyclerView.Adapter<EmpresasViewHolder> {

    public List<Empresa> emps;
    Context context;

    public InteresadasAdapter (Context context, List<Empresa> emps)
    {
        this.context=context;
        this.emps=emps;
    }


    @NonNull
    @Override
    public EmpresasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_solicitudes,viewGroup,false);
        EmpresasViewHolder viewHolder = new EmpresasViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmpresasViewHolder viewHolder, final int i) {
        viewHolder.nomEmp.setText(emps.get(i).getNom_emp());
        viewHolder.emailEmp.setText(emps.get(i).getNom_emp());
        Picasso.with(context.getApplicationContext()).load("http://3.93.218.234/"+emps.get(i).getFoto_emp()).error(R.drawable.person_icon).into(viewHolder.fotoEmp);
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                {
                    Toast.makeText(context,"Long Click "+emps.get(i).getId_emp(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(context,"Short click "+emps.get(i).getId_emp(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return emps.size();
    }

}
