package monetti.proyecto.abmalumnos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by root on 12/12/16.
 */

public class DataBase extends SQLiteOpenHelper{

    Context mContext;

    DataBase(Context mContext, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(mContext, name, factory, version);
        this.mContext = mContext;

    }

    public void onCreate(SQLiteDatabase dbUsuario) {
        String CREAT_USUARIO = "CREATE TABLE Usuario (nombreUsuario TEXT NOT NULL UNIQUE, password TEXT NOT NULL,codTipoUsuario TEXT NOT NULL, PRIMARY KEY(nombreUsuario),FOREIGN KEY(`codTipoUsuario`) REFERENCES TipoUsuario )";
        dbUsuario.execSQL(CREAT_USUARIO);
        dbUsuario.execSQL("CREATE TABLE IF NOT EXISTS alumno(dni VARCHAR, nombre VARCHAR, apellido VARCHAR,nombreUsuario VARCHAR,correo VARCHAR, password VARCHAR, paisOrigen VARCHAR, provincia VARCHAR, localidad VARCHAR, direccionCalle VARCHAR, numeracion VARCHAR, carrera VARCHAR, tipoUsuario VARCHAR);");

        // dbUsuario.execSQL("CREATE TABLE IF NOT EXISTS tipoUsuario(codTipoUsuario TEXT NOT NULL UNIQUE,descpTipoUsuario TEXT NOT NULL, PRIMARY KEY(codTipoUsuario));");

        InputStream is = null;
        try {
            is = mContext.getAssets().open("databases/usuarioDatos.sql");
            if (is != null) {
                dbUsuario.beginTransaction();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = reader.readLine();
                while (!TextUtils.isEmpty(line)) {
                    dbUsuario.execSQL(line);
                    line = reader.readLine();
                }
                dbUsuario.setTransactionSuccessful();
            }
        } catch (Exception ex) {
            // Muestra log
        } finally {
            dbUsuario.endTransaction();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // Muestra log
                }
            }
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

    }


}
