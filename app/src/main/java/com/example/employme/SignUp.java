package com.example.employme;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import OpneHelper.SQLite_OpenHelper;
import Retrofit.INodeJS;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import Retrofit.RetrofitClient;

public class SignUp extends AppCompatActivity {

    EditText nom,pass,confpass,mail,fn,u;
    String correo="aurantisoft@gmail.com";
    String contraseña="Correoempresa";
    Intent intent;
    Bundle extras;
    String tipo=null;
    RadioButton h,m;
    Session session;
    private int dia,mes,ano;
    SQLite_OpenHelper db = new SQLite_OpenHelper(this,"Employme",null,1);
    Cifrado c = new Cifrado();
    String claveAsp=null;
    String claveEmp=null;
    String s =null;
    Aspirante asp = new Aspirante();
    Empresa emp = new Empresa();

    protected void onCreate(Bundle savedInstanceState) {

        intent = getIntent();
        extras=intent.getExtras();
        tipo=extras.getString("Tipo");
        if(tipo.equals("Empresa"))
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.registro_emp);
        }
        else if(tipo.equals("Aspirante"))
        {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.registro_asp);
        }

    }

    //Para crear dialog con el calendario
    public void fecha (View view)
    {
        fn=findViewById(R.id.fn);
        final Calendar c = Calendar.getInstance();
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        ano=c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d("Fecha",Integer.toString(month));
                if(year>=ano)
                {
                    if(month>=mes)
                    {
                        Toast.makeText(getApplicationContext(),"Ingresa una fecha valida",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(month+1>=10)
                        {
                            fn.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                        }
                        else
                        {
                            fn.setText(year+"-"+0+(month+1)+"-"+dayOfMonth);
                        }
                    }
                    Toast.makeText(getApplicationContext(),"Ingresa una fecha valida",Toast.LENGTH_SHORT).show();
                    fn.setText("");
                }
                else
                            {
                                if(month+1>=10)
                                {
                                    fn.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                                }
                                else
                                {
                                    fn.setText(year+"-"+0+(month+1)+"-"+dayOfMonth);
                                }
                            }
            }

        },ano,mes,dia);
        datePickerDialog.show();
    }
    // Para regresar a la actividad anterior
    public void regresar(View view) {

        intent = new Intent(this,Activity.class);
        intent.putExtra("Tipo",tipo);
        startActivity(intent);
    }

    //Para registrar Aspirantes
    public void signUpAsp(View view) throws Exception {

        //Se crea un objeto de tipo aspirante de los cuales se usarán sus métodos get y set


        //Se obtienen los elementos através de su id
        fn=findViewById(R.id.fn);
        h=findViewById(R.id.h);
        m=findViewById(R.id.m);
        nom=findViewById(R.id.name);
        u=findViewById(R.id.user);
        pass=findViewById(R.id.password);
        confpass=findViewById(R.id.confirmPass);
        mail=findViewById(R.id.email);

        SecretKeySpec key=null;

            key=c.generarKey();
            SharedPreferences preferences = getSharedPreferences("cifrado", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
            editor.putString("claveAsp", encodedKey);
            editor.commit();


        //Si las contraseñas coinciden se prosigue a asignar los datos
        if((pass.getText().toString()).equals(confpass.getText().toString()))
        {
            //Se asignan los atributos con los datos obtenidos de la vista
            asp.setNom_asp(nom.getText().toString());
            asp.setEmail_asp(mail.getText().toString());
            asp.setUsu_asp(u.getText().toString());
            asp.setPsw_asp(c.encriptar(pass.getText().toString(),key));
            asp.setFn_asp(fn.getText().toString());
            //Se verifica que radiobutton está seleccionado y se asigna el sexo
            if(h.isChecked())
            {
                asp.setSex_asp(h.getText().toString());
            }
            else if (m.isChecked()){
                asp.setSex_asp(m.getText().toString());
            }

            //Se hace la peticion a node js para registrar los datos

            Call<String> call = RetrofitClient.getInstance().getApi().registerAsp(asp.getNom_asp(),asp.getUsu_asp(),pass.getText().toString(),confpass.getText().toString(),asp.getEmail_asp(),asp.getFn_asp(),asp.getSex_asp(),"Android");
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    try
                    {
                        s = response.body();
                        if (s.equals("Usuario o email ya registrado"))
                        {
                            Log.e("Son iguales","Verdadero");
                            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            db.abrir();
                            //Se realiza la inserción
                            db.insertarAsp(asp);
                            //Se cierra la conexión
                            db.cerrar();

                            //Se envía el correo de bienvenida y como parametro se coloca el correo del usuario
                            sendMail(mail.getText().toString());
                            //Se envia a la vista una notificacion diciendo que el usuario se registro correctamente
                            Toast.makeText(getApplicationContext(),"Usuario registrado correctamente",Toast.LENGTH_SHORT).show();

                            //Se crea un intent, para inviar a la siguiente vista y se pueda inicar sesión
                            intent = new Intent(SignUp.this,Login.class);
                            //Se envía el tipo de usuario
                            tipo="Aspirante";
                            intent.putExtra("Tipo",tipo);
                            //Se inicia el intent
                            startActivity(intent);
                        }
                    }
                    catch (Exception  e)
                    {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
                //Se abre la bd
               /*
*/



        }
        else
        {
            Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
        }

    }

    //Para registrar Empresas
    public void signUpEmp(View view) throws Exception {

        nom=findViewById(R.id.company);
        u=findViewById(R.id.userCompany);
        pass=findViewById(R.id.passCompany);
        confpass=findViewById(R.id.confirmpass);
        mail=findViewById(R.id.emailCompany);


        SecretKeySpec key=null;

            key=c.generarKey();

            SharedPreferences preferences = getSharedPreferences("cifrado", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
            editor.putString("claveEmp", encodedKey);
            editor.commit();

        if((pass.getText().toString()).equals(confpass.getText().toString())) {

            emp.setNom_emp(nom.getText().toString());
            emp.setUser_emp(u.getText().toString());
            emp.setEmail_emp(mail.getText().toString());
            emp.setPsw_emp(c.encriptar(pass.getText().toString(),key));

            //Se hace la peticion a node js para registrar los datos

            Call<String> call = RetrofitClient.getInstance()
                                                .getApi()
                                                .registerEmp(emp.getNom_emp(),emp.getUser_emp(),pass.getText().toString(),
                                                                confpass.getText().toString(),emp.getEmail_emp(),"Android");
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    s = response.body();
                    if (s.equals("Usuario o email ya registrado"))
                    {
                        Log.e("Son iguales","Verdadero");
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        sendMail(mail.getText().toString());

                        db.abrir();
                        try {
                            db.insertarEmp(emp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        db.cerrar();

                        Toast.makeText(getApplicationContext(),"Usuario registrado correctamente",Toast.LENGTH_SHORT).show();

                        intent = new Intent(SignUp.this,Login.class);
                        tipo="Empresa";
                        intent.putExtra("Tipo",tipo);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Ocurrio un error",Toast.LENGTH_SHORT).show();
                }
            });


        }
        else
        {
            Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
        }
    }

    public void sendMail(String cor)
    {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        try{

            session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correo,contraseña);
                }
            });

            if(session!=null)
            {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(correo));
                message.setSubject("Bienvenido a Employme");
                message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(cor));
                message.setContent("<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "  <head>\n" +
                        "    <title>Employ.me Email de confirmacion</title>\n" +
                        "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                        "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                        "    <style type=\"text/css\">\n" +
                        "        \n" +
                        "        body, table, td, a { -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; }\n" +
                        "        table, td { mso-table-lspace: 0pt; mso-table-rspace: 0pt; }\n" +
                        "        img { -ms-interpolation-mode: bicubic; }\n" +
                        "\n" +
                        "        \n" +
                        "        img { border: 0; height: auto; line-height: 100%; outline: none; text-decoration: none; }\n" +
                        "        table { border-collapse: collapse !important; }\n" +
                        "        body { height: 100% !important; margin: 0 !important; padding: 0 !important; width: 100% !important; }\n" +
                        "    </style>\n" +
                        "  </head>\n" +
                        "  <body style=\"background-color: black; margin: 0 !important; padding: 60px 0 60px 0 !important;\">\n" +
                        "    <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" role=\"presentation\" width=\"100%\">\n" +
                        "      <tr>\n" +
                        "          <td bgcolor=\"black\" style=\"font-size: 0;\">&\u200Bnbsp;</td>\n" +
                        "          <td bgcolor=\"black\" width=\"600\" style=\"border-radius: 4px; color: grey; font-family: sans-serif; font-size: 18px; line-height: 28px; padding: 40px 40px;\">\n" +
                        "            <article>\n" +
                        "              <h1 style=\"color: white; font-size: 32px; font-weight: bold; line-height: 36px; margin: 0 0 30px 0; text-align: center;\">Employ.me</h1>\n" +
                        "              <img alt=\"Ciudad\" src=\"https://i.imgur.com/e2TL2BM.jpg\" height=\"300\" width=\"600\" style=\"background-color: black; color: #f8c433; display: block; font-family: sans-serif; font-size: 72px; font-weight: bold; height: auto; max-width: 100%; text-align: center; width: 100%;\">\n" +
                        "              <p style=\"margin: 30px 0 30px 0;\">Bienvenido a Employ.me el sistema de bolsa de empleos ideal para personas con conocimientos enfocados al area informatica</p>\n" +
                        "              <p style=\"margin: 30px 0 30px 0; text-align: center;\">\n" +
                        "                <a href=\"Employme.com\" target=\"_blank\" style=\"font-size: 18px; font-family: sans-serif; font-weight: bold; color: black; text-decoration: none; border-radius: 8px; -webkit-border-radius: 8px; background-color: #f8c433; border-top: 20px solid #f8c433; border-bottom: 18px solid #f8c433; border-right: 40px solid #f8c433; border-left: 40px solid #f8c433; display: inline-block;\">Comenzar ahora</a>\n" +
                        "              </p>\n" +
                        "              <p style=\"margin: 0 0 30px 0;\">Este email ha sido enviado automaticamente a su correo debido a que un nuevo usuario ha sido registrado con el mismo, si usted no fue la persona que se registro <a href=\"employme.com/contacto\">ponganse en contacto con employ.me</a></p>\n" +
                        "            </article>\n" +
                        "          </td>\n" +
                        "          <td bgcolor=\"black\" style=\"font-size: 0;\">&\u200Bnbsp;</td>\n" +
                        "      </tr>\n" +
                        "    </table>\n" +
                        "  </body>\n" +
                        "</html>","text/html; charset=UTF-8");

                Transport.send(message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}




