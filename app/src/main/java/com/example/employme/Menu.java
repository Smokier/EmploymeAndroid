package com.example.employme;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import Retrofit.RetrofitClient;
import retrofit2.Call;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

import Fragments.ConfiguracionFragment;
import Fragments.PerfilFragment;
import Fragments.SolicitudesFragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu extends AppCompatActivity {

    ImageView imageView;
    Intent intent;
    Bundle extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        intent = getIntent();
        extras = intent.getExtras();
        BottomNavigationView bottonNav = findViewById(R.id.menu_navegacion);
        bottonNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new PerfilFragment()).commit();
        imageView=findViewById(R.id.fp);

        imageView.setVisibility(View.INVISIBLE);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId())
            {
                case R.id.perfil: selectedFragment = new PerfilFragment();

                    break;
                case R.id.opciones: selectedFragment = new ConfiguracionFragment();
                    break;
                case R.id.soli: selectedFragment = new SolicitudesFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

            return true;
        }
    };


    public void onClack(View view){
        cargarImagen();
    }

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la aplicación"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){

            Uri imgUri =  data.getData();
            getPath(imgUri);


        }
    }

    public void getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        Toast.makeText(this, cursor.getString(column_index), Toast.LENGTH_SHORT).show();
        File file = new File(cursor.getString(column_index));

        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"),extras.getString("Id"));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part requestImage;

        requestImage = MultipartBody.Part.createFormData("file",file.getName(),requestFile);

        Call<String> call = RetrofitClient.getInstance().getApi().uploadPhoto(requestImage,description);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getApplicationContext(),"Imagen Actualizada",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Menu.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });





    }



}
