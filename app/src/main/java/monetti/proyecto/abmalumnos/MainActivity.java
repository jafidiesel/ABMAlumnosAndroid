package monetti.proyecto.abmalumnos;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    SQLiteDatabase db;
    Button buttonAlta, buttonBaja, buttonVer, buttonOpciones;

    @Override //Constructor por defecto
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.activity_main);
        buttonAlta      = (Button) findViewById(R.id.buttonAlta);
        buttonBaja      = (Button) findViewById(R.id.buttonBaja);
        buttonVer       = (Button) findViewById(R.id.buttonVer);
        buttonOpciones = (Button) findViewById(R.id.buttonOpciones);

        buttonAlta.setOnClickListener(this);
        buttonBaja.setOnClickListener(this);
        buttonVer.setOnClickListener(this);
        buttonOpciones.setOnClickListener(this);

        db=openOrCreateDatabase("DBAlumnos", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS alumno(dni VARCHAR, nombre VARCHAR, apellido VARCHAR,nombreUsuario VARCHAR,correo VARCHAR, password VARCHAR, paisOrigen VARCHAR, provincia VARCHAR, localidad VARCHAR, direccionCalle VARCHAR, numeracion VARCHAR, carrera VARCHAR);");
    }


    public void onClick(View view) {

        if(view == buttonAlta){
            Intent intent = new Intent(this, altaUsuario.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }
        if(view == buttonBaja){
            Intent i = new Intent(this, bajaUsuario.class );
            startActivity(i);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }
        if (view == buttonVer){
            Intent i = new Intent(this, verUsuario.class);
            startActivity(i);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }
        if (view == buttonOpciones){
            Intent i = new Intent(this, opciones.class);
            startActivity(i);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);

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
