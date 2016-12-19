package monetti.proyecto.abmalumnos;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


    /*
    * En esta clase restringe las opciones (botones) segun el nivel de permisos de usuario
        * Si es Administrador puede acceder a todas las funciones (todos los botones)
        * Si es Alumno puede acceder a:
            * VerDatos, Modificar y Volver
    * */


public class SecondActivity extends AppCompatActivity implements View.OnClickListener{
    SQLiteDatabase db;
    Button buttonAlta, buttonBaja, buttonVer, buttonOpciones, buttonModificar, buttonVolver;
    ArrayList tipoUsuarioCod;

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
        buttonVolver = (Button) findViewById(R.id.buttonVolver);

        tipoUsuarioCod = ComunicadorClases.getObject();


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
        buttonVolver.setOnClickListener(this);

        db=openOrCreateDatabase("DBAlumnos", Context.MODE_PRIVATE, null);

    }

    /*
    * function onClick
    * @param View view - Recibe un view segun el boton clickeado
    * Si se es usuario Administrador
        * Si clickea el boton buttonAlta:
            *  A traves del ComunicadorClases setea la opcion "Alta" para que usemos el xml "alta_usuario"
            *  para ingresar un usuario
            *
        * Si clickea el boton buttonBaja
            * se va al activity baja_usuario.xml
            *
        * Si clickea el boton buttonVer
            * se va al activity ver_usuario.xml
            *
        * Si clickea el boton buttonModificar
            * se va al activity activity_modificar_alumno.xml
            *
        * Si clickea el boton buttonOpciones
            * se va al activity activity_opciones.xml
            *
        * Si clickea el boton buttonVolver
            * se va al activity activity_main.xml
            *
    * Si se es usuario Alumno
        * Si clickea el boton buttonVer
            * se va al activity ver_usuario.xml
            *
        * Si clickea el boton buttonModificar
            *  A traves del ComunicadorClases setea la opcion "Modificar" para que usemos el xml "alta_usuario"
            *  para poder editar cada campo
            *
        * Si clickea el boton buttonVolver
            * se va al activity activity_main.xml
    * */

    public void onClick(View view) {


        if(view == buttonAlta){
            ComunicadorClases.setOpcion("Alta");
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
            if (tipoUsuarioCod.get(0) == "usrAlm") {
                Intent i = new Intent(this, altaUsuario.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }else if(tipoUsuarioCod.get(0) == "usrAdm"){

                Intent i = new Intent(this, ModificarAlumno.class);
                startActivity(i);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);
            }
        } if  (view == buttonVolver) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }


    }



}
