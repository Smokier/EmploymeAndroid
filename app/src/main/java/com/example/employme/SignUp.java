package com.example.employme;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Base64;
import java.util.Calendar;
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
    SecretKey originalKey;


    protected void onCreate(Bundle savedInstanceState) {

        intent = getIntent();
        extras=intent.getExtras();
        tipo=extras.getString("Tipo");
        if(tipo.equals("Empresa"))
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.registro_emp);
            cargarPreferenciasEmp();
        }
        else if(tipo.equals("Aspirante"))
        {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.registro_asp);
            cargarPreferenciasAsp();
        }




    }

    private void cargarPreferenciasEmp() {
        SharedPreferences preferences = getSharedPreferences("cifrado", Context.MODE_PRIVATE);
        claveEmp = preferences.getString("claveEmp","No existe la información");
        Toast.makeText(this,claveEmp,Toast.LENGTH_SHORT).show();

    }

    private void cargarPreferenciasAsp() {
        SharedPreferences preferences = getSharedPreferences("cifrado", Context.MODE_PRIVATE);
        claveAsp = preferences.getString("claveAsp","No existe la información");
        Toast.makeText(this,claveAsp,Toast.LENGTH_SHORT).show();

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
                fn.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        },dia,mes,ano);
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
        Aspirante asp = new Aspirante();

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
        if(claveAsp=="No existe la información")
        {
            key=c.generarKey();


            SharedPreferences preferences = getSharedPreferences("cifrado", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
            editor.putString("claveAsp", encodedKey);
            editor.commit();
        }
        else
        {
            byte[] decodedKey = Base64.getDecoder().decode(claveAsp);
            originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        }




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
            //Se envía el correo de bienvenida y como parametro se coloca el correo del usuario
            sendMail(mail.getText().toString());
            //Se abre la bd
            db.abrir();
            //Se realiza la inseción
            db.insertarAsp(asp);
            //Se cierra la conexión
            db.cerrar();

            //Se envia a la vista una notificacion diciendo que el usuario se registro correctamente
            Toast.makeText(getApplicationContext(),"Usuario registrado correctamente",Toast.LENGTH_SHORT).show();

            //Se crea un intent, para inviar a la siguiente vista y se pueda inicar sesión
            intent = new Intent(this,Login.class);
            //Se envía el tipo de usuario
            tipo="Aspirante";
            intent.putExtra("Tipo",tipo);
            //Se inicia el intent
            startActivity(intent);

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
        }

    }


    public void signUpEmp(View view) throws Exception {

        nom=findViewById(R.id.company);
        pass=findViewById(R.id.passCompany);
        confpass=findViewById(R.id.confirmpass);
        mail=findViewById(R.id.emailCompany);
        Empresa emp = new Empresa();

        SecretKeySpec key=null;

        if(claveEmp=="No existe la información")
        {
            key=c.generarKey();

            SharedPreferences preferences = getSharedPreferences("cifrado", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
            editor.putString("claveEmp", encodedKey);
            editor.commit();
        }
        else
        {
            byte[] decodedKey = Base64.getDecoder().decode(claveAsp);
            originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        }



        if((pass.getText().toString()).equals(confpass.getText().toString())) {




            emp.setNom_emp(nom.getText().toString());
            emp.setEmail_emp(mail.getText().toString());
            emp.setPsw_emp(c.encriptar(pass.getText().toString(),key));
            sendMail(mail.getText().toString());

            db.abrir();
            db.insertarEmp(emp);
            db.cerrar();

            Toast.makeText(getApplicationContext(),"Usuario registrado correctamente",Toast.LENGTH_SHORT).show();

            intent = new Intent(this,Login.class);
            tipo="Empresa";
            intent.putExtra("Tipo",tipo);
            startActivity(intent);

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
                message.setContent("<h1> Se ha enviado la primer prueba con JavaMail</h1>","text/html; charset=UTF-8");

                Transport.send(message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}




