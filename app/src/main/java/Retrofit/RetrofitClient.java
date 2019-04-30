package Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance;
    private static final String Base_URL="http://3.93.218.234";
    private Retrofit retrofit;

    private RetrofitClient ()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit= new Retrofit.Builder()
                              .baseUrl(Base_URL)
                              .addConverterFactory(ScalarsConverterFactory.create())
                              .addConverterFactory(GsonConverterFactory.create(gson))
                              .build();
    }


    public static synchronized RetrofitClient getInstance()
    {
        if(instance==null)
        {
            instance=new RetrofitClient();
        }
        return instance;
    }
    public INodeJS getApi()
    {
        return retrofit.create(INodeJS.class);
    }
   /* public static Retrofit getInstance()
    {
        if(instance==null)

            instance= new Retrofit.Builder()
                    .baseUrl("http://34.227.162.181:8080")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return instance;
    }

    public static synchronized Retrofit getInstance()
    {

    }
    public static Retrofit getInstance2()
    {
        if(instance==null)

            instance= new Retrofit.Builder()
                    .baseUrl("http://34.227.162.181:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return instance;
    }*/
}
