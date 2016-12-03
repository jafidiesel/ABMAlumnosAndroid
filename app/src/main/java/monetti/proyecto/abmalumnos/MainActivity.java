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
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    SQLiteDatabase db;
    Button buttonAlta, buttonBaja, buttonVer, buttonDeleteAll;

    @Override //Constructor por defecto
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.activity_main);
        buttonAlta      = (Button) findViewById(R.id.buttonAlta);
        buttonBaja      = (Button) findViewById(R.id.buttonBaja);
        buttonVer       = (Button) findViewById(R.id.buttonVer);
        buttonDeleteAll = (Button) findViewById(R.id.buttonDeleteAll);

        buttonAlta.setOnClickListener(this);
        buttonBaja.setOnClickListener(this);
        buttonVer.setOnClickListener(this);
        buttonDeleteAll.setOnClickListener(this);

        db=openOrCreateDatabase("DBAlumnos", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS alumno(dni VARCHAR, nombre VARCHAR, apellido VARCHAR,nombreUsuario VARCHAR,correo VARCHAR, contrasenia VARCHAR, paisOrigen VARCHAR, provincia VARCHAR, localidad VARCHAR, direccion VARCHAR, carrera VARCHAR);");
    }


    public void onClick(View view) {

        if(view == buttonAlta){
            Intent intent = new Intent(this, altaUsuario.class);
            startActivity(intent);
        }
        if(view == buttonBaja){
            Intent i = new Intent(this, bajaUsuario.class );
            startActivity(i);
        }
        if (view == buttonVer){
            Intent i = new Intent(this, verUsuario.class);
            startActivity(i);
        }
        if (view == buttonDeleteAll){
            try {
                db.execSQL("DELETE FROM alumno WHERE 1");
                Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
                showMessage("Drop Table","Datos de tabla alumno eliminados exitosamente");
            } catch (Exception e){
                showMessage("Error","Error eliminando la DB");
            }
        }

    }


    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
