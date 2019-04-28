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
import android.widget.ImageView;
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
    String tipo=null,nombre=null,mail=null,username=null,foto=null,contra=null;
    TextView name,pass,email,nameGit,userGit;
    ImageView imageView;
    ListView listView;
    private YouTubePlayer YPlayer;
    String claveYT="AIzaSyCAzlL92LzYUtlSO7cyEyo-dxAsStKZlEw";
    Intent intent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perfil,container,false);

        final String [] data = null;
        intent = getActivity().getIntent();
        extras=intent.getExtras();

        tipo=extras.getString("Tipo");
        nombre=extras.getString("Nombre");
        mail=extras.getString("Email");
        username=extras.getString("Username");
        contra=extras.getString("Pass");
        foto=extras.getString("Foto");


        name=v.findViewById(R.id.name);
        userGit=v.findViewById(R.id.userGit);
        nameGit=v.findViewById(R.id.name_git);
        pass=v.findViewById(R.id.contra);
        email=v.findViewById(R.id.mail);
        imageView=v.findViewById(R.id.foto_perfil);
        listView=v.findViewById(R.id.lista);


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




//Para foto
        Call<Aspirante> call = RetrofitClient
                .getInstance()
                .getApi()
                //Se ejecuta el metodo y se envían como parametros el correo o username y contraseña del usuario
                .getFoto(extras.getString("Id"),"Android");
        call.enqueue(new Callback<Aspirante>() {
            @Override
            public void onResponse(Call<Aspirante> call, Response<Aspirante> response) {

                Picasso.with(getActivity()).load("http://34.227.162.181:8080/"+response.body().getFoto_asp()).into(imageView);
            }

            @Override
            public void onFailure(Call<Aspirante> call, Throwable t) {

            }
        });

        //Para repositorios
        Call<List<Github>> repos= RetrofitClient.getInstance().getApi().getRepositories(extras.getString("Id"),"Android");
        repos.enqueue(new Callback<List<Github>>() {
            @Override
            public void onResponse(Call<List<Github>> call, Response<List<Github>> response) {

                List<Github> git = response.body();
                String [] repos = new String[git.size()];
                String [] links = new String[git.size()];

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
        name.setText(nombre);
        pass.setText(contra);
        email.setText(mail);
        return v;
    }




}
