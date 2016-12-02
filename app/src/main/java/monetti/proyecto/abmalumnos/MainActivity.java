package monetti.proyecto.abmalumnos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;

    @Override //Constructor por defecto
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.activity_main);
        db=openOrCreateDatabase("DBAlumnos", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS alumno(dni VARCHAR, nombre VARCHAR, apellido VARCHAR,nombreUsuario VARCHAR,correo VARCHAR, contrasenia VARCHAR, paisOrigen VARCHAR, provincia VARCHAR, localidad VARCHAR, direccion VARCHAR, carrera VARCHAR);");
    }

    //Cambio de MainActivity a altaUsuario
    public void altaUsuario(View view) {
        Intent intent = new Intent(this, altaUsuario.class);
        startActivity(intent);
    }


    //Cambio de MainActivity a bajaUsuario
    public void bajaUsuario(View view) {
        Intent i = new Intent(this, bajaUsuario.class );
        startActivity(i);
    }

    public void deleteAll(View view){
        try {
            db.execSQL("DELETE FROM alumno WHERE 1");
            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        } catch (Exception e){
            messageBox("Error","Error eliminando la DB");
        }
    }


    //*********************************************************
    //generic dialog, takes in the method name and error message
    //*********************************************************
    private void messageBox(String method, String message)
    {
        Log.d("EXCEPTION: " + method,  message);

        AlertDialog.Builder messageBox = new AlertDialog.Builder(this);
        messageBox.setTitle(method);
        messageBox.setMessage(message);
        messageBox.setCancelable(false);
        messageBox.setNeutralButton("OK", null);
        messageBox.show();
    }

}
