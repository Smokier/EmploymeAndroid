package Photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import Retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadPhoto extends AsyncTask<Void,Void,Void> {

    Bitmap image;
    Context context;
    String id;

    public UploadPhoto(Bitmap image, Context context,String id) {
        this.image=image;
        this.context=context;
        this.id=id;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
        Call<String> call = RetrofitClient.getInstance().getApi().uploadPhoto(encodedImage,"Android",id);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        super.onPostExecute(voids);
    }
}
