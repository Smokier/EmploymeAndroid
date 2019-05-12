package Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employme.Aspirante;
import com.example.employme.Github;
import com.example.employme.R;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragment extends Fragment {

    Bundle extras;
    String tipo,nombre,mail,username,foto,contra,usergh,vid;
    LinearLayout datos;
    TextView name,pass,email,nameGit,userGit;
    Button cambio;
    ImageView imageView;
    ListView listView;
    private YouTubePlayer YPlayer;
    String claveYT="AIzaSyCAzlL92LzYUtlSO7cyEyo-dxAsStKZlEw";
    Intent intent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_perfil,container,false);

        intent = getActivity().getIntent();
        extras=intent.getExtras();

        tipo=extras.getString("Tipo");
        nombre=extras.getString("Nombre");
        mail=extras.getString("Email");
        username=extras.getString("Username");
        contra=extras.getString("Pass");
        foto=extras.getString("Foto");
        usergh=extras.getString("UserGit");
        vid= extras.getString("Video");

        name=v.findViewById(R.id.name);
        name.setEnabled(false);

        userGit=v.findViewById(R.id.userGit);
        nameGit=v.findViewById(R.id.name_git);

        pass=v.findViewById(R.id.contra);
        pass.setEnabled(false);

        email=v.findViewById(R.id.mail);
        email.setEnabled(false);

        imageView=v.findViewById(R.id.foto_perfil);
        listView=v.findViewById(R.id.lista);
        datos=v.findViewById(R.id.datos);

        datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });


        cambio=v.findViewById(R.id.cambios);

        cambio.setVisibility(View.INVISIBLE);





if(vid==null)
{
Toast.makeText(getContext(),"Al parecer no haz enlazado un video de youtube",Toast.LENGTH_SHORT).show();
}
else
{
    //Para video
    Call<Aspirante> video = RetrofitClient.getInstance().getApi().getVideo(extras.getString("Id"),"Android");

    video.enqueue(new Callback<Aspirante>() {
        @Override
        public void onResponse(Call<Aspirante> call, final Response<Aspirante> response) {


            final YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.youtube_player_fragment, youTubePlayerFragment);
            transaction.commit();
            youTubePlayerFragment.initialize(claveYT, new YouTubePlayer.OnInitializedListener() {

                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                    if (!b) {
                        YPlayer = youTubePlayer;
                        YPlayer.cueVideo(response.body().getVyt_pasp());
                    }
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                    if (arg1.isUserRecoverableError())
                    {
                        arg1.getErrorDialog(getActivity(),1);
                    }
                    else
                    {
                        String error="Error al inicializar Youtube"+arg1.toString();
                        Toast.makeText(getContext(),error,Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        @Override
        public void onFailure(Call<Aspirante> call, Throwable t) {

        }
    });
}





//Para foto
        Call<Aspirante> call = RetrofitClient
                .getInstance()
                .getApi()
                //Se ejecuta el metodo y se envían como parametros el correo o username y contraseña del usuario
                .getFoto(extras.getString("Id"),"Android");
        call.enqueue(new Callback<Aspirante>() {
            @Override
            public void onResponse(Call<Aspirante> call, Response<Aspirante> response) {

                Picasso.with(getContext()).load("http://3.93.218.234/"+response.body().getFoto_asp()).into(imageView);
            }

            @Override
            public void onFailure(Call<Aspirante> call, Throwable t) {

            }
        });

        if(usergh==null)
        {
            Toast.makeText(getContext(),"Al parecer no haz enlazado tu cuenta de Github",Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Para repositorios
            Call<List<Github>> repos= RetrofitClient.getInstance().getApi().getRepositories(extras.getString("Id"),"Android");
            repos.enqueue(new Callback<List<Github>>() {
                @Override
                public void onResponse(Call<List<Github>> call, Response<List<Github>> response) {

                    List<Github> git = response.body();
                    String [] repos = new String[git.size()];

                    for (Github g: git)
                    {
                        String [] userdata = g.getFull_name().split("/");
                        String user = userdata[0];
                        nameGit.setText(user);
                        userGit.setText("github.com/"+user);
                    }
                    for (int i =0; i<git.size();i++)
                    {
                        repos[i]=git.get(i).getName();
                    }
                    listView.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,repos));
                }

                @Override
                public void onFailure(Call<List<Github>> call, Throwable t) {
                    Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://github.com/"+nameGit.getText().toString()+"/"+listView.getItemAtPosition(position).toString()));
                    startActivity(intent);
                }
            });
        }


        name.setText(nombre);
        pass.setText(contra);
        email.setText(mail);
        return v;
    }

    public void edit()
    {
        email.setEnabled(true);
        pass.setEnabled(true);
        cambio.setVisibility(View.VISIBLE);

        cambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals(extras.getString("Email")))
                {
                    Toast.makeText(getContext(),"No modificaste ningun dato",Toast.LENGTH_SHORT).show();
                    cambio.setVisibility(View.INVISIBLE);
                    email.setEnabled(false);
                    pass.setEnabled(false);
                }
                else
                {

                    Call<String> call = RetrofitClient.getInstance().getApi().updateAsp(email.getText().toString(),"Android",extras.getString("Id"));
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            intent = getActivity().getIntent();
                            intent.putExtra("Email",email.getText().toString());
                            getActivity().finish();
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }





}
