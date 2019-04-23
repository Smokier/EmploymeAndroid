package Retrofit;

import android.provider.CallLog;

import com.example.employme.Aspirante;
import com.example.employme.Empresa;
import com.example.employme.Github;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface INodeJS  {

    @POST("/regasp")
    @FormUrlEncoded
    Call<String>registerAsp(@Field("nombre") String nombre,@Field("usuario")String usuario,@Field("password") String password,@Field("confpass") String confpass,
                                  @Field("email") String email,@Field("fn") String fn, @Field("sexo") String sexo,@Field ("device") String device);
    @POST("/regemp")
    @FormUrlEncoded
    Call<String>registerEmp(@Field("nombre_e") String nom,@Field("usuario_e") String user,@Field("password_e") String pass,@Field("cpassword_e")String cpass,@Field("email_e") String email,@Field("device") String device);

    @POST("/loginasp")
    @FormUrlEncoded
    Call<Aspirante>loginAsp(@Field("usu")String user,@Field("contra")String pass,@Field("device")String device);

    @POST("/loginemp")
    @FormUrlEncoded
    Call<Empresa>loginEmp(@Field("usu_e")String user,@Field("pass_e")String pass,@Field("device")String device);

    @PUT("/fotoasp")
    @FormUrlEncoded
    Call<Aspirante>getFoto(@Field("id")String id,@Field("device")String dev);

    @POST("/perfilasp")
    @FormUrlEncoded
    Call<List<Github>> getRepositories(@Field("id")String id, @Field("device")String dev);

}
