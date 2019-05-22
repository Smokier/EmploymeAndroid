package Retrofit;

import com.example.employme.Aspirante;
import com.example.employme.Empresa;
import com.example.employme.Github;

import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface INodeJS  {

    //Aquí es donde se configuran las peticiones hacia el servidor web, el cual regresa o no datos, depende el tipo de petición.

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

    @PUT("/perfilasp")
    @FormUrlEncoded
    Call<Aspirante> getVideo(@Field("id")String id,@Field("device")String dev);

    @POST("/asp-interested")
    @FormUrlEncoded
    Call<List<Empresa>> getInteresadass(@Field("id")String id,@Field("device")String dev);

    @POST("/aspirante/asps")
    @FormUrlEncoded
    Call<List<Aspirante>> getAspirantes(@Field("device") String dev);

    @POST("/update")
    @FormUrlEncoded
    Call<String> updateAsp(@Field("correo") String c,@Field("device") String dev,@Field("id") String id);

    @Multipart
    @POST("fotoasp/uploadPhoto")
    Call <String> uploadPhoto(@Part MultipartBody.Part photo,@Part ("Id") RequestBody  userId);

    @Multipart
    @POST("fotoemp/uploadPhoto")
    Call<String> uploadPhotoEmp(@Part MultipartBody.Part photo,@Part ("Id") RequestBody  userId);


    @POST("/aspirantes/interesAndroid")
    @FormUrlEncoded
    Call<String> interes(@Field("idEmp") String idE,@Field("NameEmp") String name, @Field("idAsp") String idA, @Field("emailAsp") String emailAsp, @Field("device") String dev);

    @POST("/aspirantes/knowAndroid")
    @FormUrlEncoded
    Call<String> getInteres(@Field("idEmp") String idE, @Field("idAsp") String idA, @Field("device") String dv);

    @POST("/fotoemp/foto")
    @FormUrlEncoded
    Call<String> getFotoEmp(@Field("id") String id, @Field("device") String dev);

    @POST("/updateE/show")
    @FormUrlEncoded
    Call<Empresa> getDataEmp(@Field("id") String id, @Field("device")String dev);
}
