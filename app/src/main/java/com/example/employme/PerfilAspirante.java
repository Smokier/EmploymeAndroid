package com.example.employme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import java.util.List;

import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilAspirante extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    Intent intent;
    Bundle extras;
    String nombre, correo, foto;

    ImageView pic;
    EditText nom;
    EditText c;
    TextView nameGit,userGit;
    LinearLayout p;
    ListView listView;
    YouTubePlayerView player;
    Button i;
    String claveYT="AIzaSyCAzlL92LzYUtlSO7cyEyo-dxAsStKZlEw";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aspirante_profile);

        intent = getIntent();
        extras = intent.getExtras();
        nombre = extras.getString("Nombre");
        correo = extras.getString("Email");
        foto = extras.getString("Foto");

        pic = findViewById(R.id.foto_perfil);
        nom = findViewById(R.id.name);
        c = findViewById(R.id.mail);
        userGit=findViewById(R.id.userGit);
        nameGit=findViewById(R.id.name_git);
        listView=findViewById(R.id.lista);
        player= findViewById(R.id.youtube_player);
        i = findViewById(R.id.interes);



        c.setText(correo);
        nom.setText(nombre);
        nom.setEnabled(false);
        c.setEnabled(false);
        Picasso.with(getApplicationContext()).load("http://34.227.162.181/" + foto).error(R.drawable.person_icon).into(pic);

        repositories();
        if(extras.getString("Video")!= null)
        {
            Toast.makeText(this, extras.getString("Video"), Toast.LENGTH_SHORT).show();
            player.initialize(claveYT,this);
        }
        getInteres();


    }

    public String [] datos (){

        SharedPreferences data = getSharedPreferences("sessionEmp",Context.MODE_PRIVATE);
        String user=data.getString("IdEmp","No existe la info");
        String nameEmp = data.getString("NomEmp","No existe la info");
        Toast.makeText(getApplicationContext(),"Id emp:"+user+" id asp: "+extras.getString("Id"),Toast.LENGTH_LONG).show();

        String d [] = null;
        d[0] = user;
        d[1]= nameEmp;
        return d;
    }

    public void getInteres()
    {
        String [] data = datos ();
        Call<String> in = RetrofitClient.getInstance().getApi().getInteres(data[0],extras.getString("Id"),"Android");

        in.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(PerfilAspirante.this, response.body(), Toast.LENGTH_SHORT).show();
                if(response.body().equals("true"))
                {
                    i.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void repositories()
    {
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
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,repos));
            }

            @Override
            public void onFailure(Call<List<Github>> call, Throwable t) {
                //Toast.makeText(getApplicationContext(),"Cuenta de github no enlazada",Toast.LENGTH_SHORT).show();
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


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b)
        {
            youTubePlayer.cueVideo(extras.getString("Video"));
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    public void interes(View view) {

    String data [] = datos();

        Call <String> call = RetrofitClient.getInstance().getApi().interes(data[0],data[1],extras.getString("Id"),correo,"Android");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getApplicationContext(),response.body(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        i.setVisibility(View.INVISIBLE);

    }


}
