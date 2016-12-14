package monetti.proyecto.abmalumnos;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{
    SQLiteDatabase db;
    Button buttonAlta, buttonBaja, buttonVer, buttonOpciones, buttonModificar;

    @Override //Constructor por defecto
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.activity_second);

        buttonAlta      = (Button) findViewById(R.id.buttonAlta);
        buttonBaja      = (Button) findViewById(R.id.buttonBaja);
        buttonVer       = (Button) findViewById(R.id.buttonVer);
        buttonOpciones = (Button) findViewById(R.id.buttonOpciones);
        buttonModificar = (Button) findViewById(R.id.buttonModificar);

        ArrayList tipoUsuarioCod = ComunicadorClases.getObject();


        if (tipoUsuarioCod.get(0) == "usrAlm"){
            buttonAlta.setVisibility(View.INVISIBLE);
            buttonBaja.setVisibility(View.INVISIBLE);
            buttonOpciones.setVisibility(View.INVISIBLE);
        }

        buttonAlta.setOnClickListener(this);
        buttonBaja.setOnClickListener(this);
        buttonVer.setOnClickListener(this);
        buttonOpciones.setOnClickListener(this);
        buttonModificar.setOnClickListener(this);

        db=openOrCreateDatabase("DBAlumnos", Context.MODE_PRIVATE, null);

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

        if (view == buttonModificar){
            ComunicadorClases.setOpcion("Modificar");
            Intent i = new Intent(this, altaUsuario.class);
            startActivity(i);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);

        }


    }



}
