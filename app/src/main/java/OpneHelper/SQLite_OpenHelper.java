package OpneHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.employme.Aspirante;

public class SQLite_OpenHelper extends SQLiteOpenHelper{
    public SQLite_OpenHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String datosaspirante = "create table datosaspirante (id_asp INTEGER primary key AUTOINCREMENT ,nom_asp text,email_asp text ,usu_asp text ,psw_asp text, FN_asp text,sex_asp text,numtel_asp text);";

        String perfilaspirante="create table periflaspirante (id_pasp integer  primary key autoincrement, id_asp integer, usugh_pasp text ,vyt_pasp text, foreign key (id_asp) references datosaspirante (id_asp) on delete cascade on update cascade);";

        String datosempresa="create table datosempresa (id_emp integer  primary key autoincrement,usu_emp text ,psw_emp text, email_emp text, nom_emp text);";

        String perfilempresa="create table perfilempresa (id_pemp integer  primary key autoincrement, id_emp integer, sitio_pemp text, numtel_pemp text, foreign key (id_emp) references datosempresa (id_emp) on delete cascade on update cascade);";

        String interes="create table interes (id_inte integer  primary key autoincrement, id_emp integer,id_asp integer, foreign key (id_emp) references datosempresa (id_emp) on delete cascade on update cascade,foreign key (id_asp) references datosasírante (id_asp) on delete cascade on update cascade);";

        db.execSQL(datosaspirante);
        db.execSQL(perfilaspirante);
        db.execSQL(datosempresa);
        db.execSQL(perfilempresa);
        db.execSQL(interes);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void abrir ()
    {
        this.getWritableDatabase();
    }

    public void cerrar()
    {
        this.close();
    }

    //Metodo para registrar aspirantes
    public void insertarAsp(Aspirante a)
    {
        ContentValues data = new ContentValues();

        data.put("nom_asp",a.getNom_asp());
        data.put("email_asp",a.getEmail_asp());
        data.put("usu_asp",a.getUsu_asp());
        data.put("psw_asp",a.getPsw_asp());
        /*data.put("FN_asp",a.getFn_asp());
        data.put("sex_asp",a.getSex_asp());
        data.put("numtel_asp",a.getNumtel_asp());*/

        this.getWritableDatabase().insert("datosaspirante",null,data);
    }

    //metodo para verificar si el aspirante esta registrado
    public Cursor consultarAspirante(String usu, String pass) throws SQLException
    {
        Cursor mcursor =null;
        mcursor=this.getReadableDatabase().query("datosaspirante",new String[]{"id_asp","nom_asp","email_asp","usu_asp","psw_asp"},"email_asp like'"+usu+"'and psw_asp like '"+pass+"'",null,null,null,null);

        return mcursor;

    }
}
