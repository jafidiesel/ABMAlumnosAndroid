package monetti.proyecto.abmalumnos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class opciones extends AppCompatActivity implements View.OnClickListener{

    //Declaracion de variables
    Button buttonDeleteAll, buttonAddRandom, buttonVolver;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Metodo que previene el cierre inesperado de la aplicacion.
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.activity_opciones);

        buttonDeleteAll      = (Button) findViewById(R.id.buttonDeleteAll);
        buttonAddRandom      = (Button) findViewById(R.id.buttonAddRandom);
        buttonVolver      = (Button) findViewById(R.id.buttonVolver);

        //se abre la conexion con la BD creada en SecondActivity.java
        db=openOrCreateDatabase("DBAlumnos", Context.MODE_PRIVATE, null);

        //Los botones estan escuchando para cuando sean clickeados
        buttonAddRandom.setOnClickListener(this);
        buttonDeleteAll.setOnClickListener(this);
        buttonVolver.setOnClickListener(this);
    }



    /*
        * Funcion onClick
        * privacidad Public
        * Devuelve void
            * @param View view - recibe una View sobre la cual consulta que boton ha sido clickeado
            * Si cliqueo en el boton 'buttonDeleteAll' elimina todos los datos de la BD
            * Si cliqueo el boton 'buttonAddRandom' ingresa datos de prueba
                * Si no existen datos con el nombre de usuario 'NOMBREUSUARIOPRUEBA' agrega dos alumnos con dastos de prueba
                * Si existe al menos un alumno con nombre de usuario 'NOMBREUSUARIOPRUEBA' nos impide agregarlos y nos muestra los datos existentes
            * Si cliqueo el boton 'buttonVolver' vuelve a la activity_second.xmlml
        *
        * */
    public void onClick(View view){

        if (view == buttonDeleteAll) {
        try {
            db.execSQL("DELETE FROM alumno WHERE 1");
            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
            showMessage("Drop Table","La BD ha sido limpiada exitosamente.");
        } catch (Exception e){
            showMessage("Error","Error eliminando la DB");
        }
        }else if(view == buttonAddRandom){

            try{
                String Query = "SELECT * FROM alumno WHERE nombreUsuario = 'NOMBREUSUARIOPRUEBA'";
                Cursor c = db.rawQuery(Query,null);
                StringBuffer bufferAlumno = new StringBuffer();
                if (c.getCount() > 0){

                    bufferAlumno.append("Ya existen datos de prueba en la BD: \n");
                    bufferAlumno.append("___________________" + "\n");

                    while (c.moveToNext()) {
                        bufferAlumno.append("DNI: " + c.getString(0) + "\n");
                        bufferAlumno.append("Nombre: " + c.getString(1) + "\n");
                        bufferAlumno.append("Apellido: " + c.getString(2) + "\n");
                        bufferAlumno.append("Nombre Usuario: " + c.getString(3) + "\n");
                        bufferAlumno.append("Correo: " + c.getString(4) + "\n");
                        bufferAlumno.append("Contraseña: " + c.getString(5) + "\n");
                        bufferAlumno.append("Pais de Origen: " + c.getString(6) + "\n");
                        bufferAlumno.append("Provincia: " + c.getString(7) + "\n");
                        bufferAlumno.append("Localidad: " + c.getString(8) + "\n");
                        bufferAlumno.append("Dirección: " + "\n\tCalle: "+ c.getString(9) + "\tN°: " + c.getString(10) +"\n");
                        bufferAlumno.append("Carrera: " + c.getString(11) + "\n");
                        bufferAlumno.append("___________________" + "\n");
                    }
                    showMessage("Datos  de prueba existentes", bufferAlumno.toString());

                } else if(c.getCount() == 0){
                    String queryInsert = "INSERT INTO alumno VALUES('1234567','NOMBREPRUEBA','APELLIDOPRUEBA','NOMBREUSUARIOPRUEBA','Correo@prueba.com','contrasenia','Argentina','Mendoza','Guaymallen','CALLEPRUEBA','123','Ing. en Sistemas de Información');";
                    String queryInsert2 = "INSERT INTO alumno VALUES('7654321','NOMBREPRUEBA2','APELLIDOPRUEBA2','NOMBREUSUARIOPRUEBA','Correo2@prueba.com','contrasenia2','Argentina','Mendoza','Guaymallen','CALLEPRUEBA2','1234','Ing. en Sistemas de Información');";
                    showMessage("Query construida",queryInsert);
                    showMessage("Query construida",queryInsert2);
                    db.execSQL(queryInsert);
                    db.execSQL(queryInsert2);

                    String postQuery = "SELECT * FROM alumno WHERE nombreUsuario = 'NOMBREUSUARIOPRUEBA'";
                    Cursor c2 = db.rawQuery(postQuery,null);

                    while (c2.moveToNext()) {
                        bufferAlumno.append("DNI: " + c2.getString(0) + "\n");
                        bufferAlumno.append("Nombre: " + c2.getString(1) + "\n");
                        bufferAlumno.append("Apellido: " + c2.getString(2) + "\n");
                        bufferAlumno.append("Nombre Usuario: " + c2.getString(3) + "\n");
                        bufferAlumno.append("Correo: " + c2.getString(4) + "\n");
                        bufferAlumno.append("Contraseña: " + c2.getString(5) + "\n");
                        bufferAlumno.append("Pais de Origen: " + c2.getString(6) + "\n");
                        bufferAlumno.append("Provincia: " + c2.getString(7) + "\n");
                        bufferAlumno.append("Localidad: " + c2.getString(8) + "\n");
                        bufferAlumno.append("Dirección: " + "\n\tCalle: "+ c2.getString(9) + "\tN°: " + c2.getString(10) +"\n");
                        bufferAlumno.append("Carrera: " + c2.getString(11) + "\n");
                        bufferAlumno.append("___________________" + "\n");
                    }
                    showMessage("Alumno ingresado", bufferAlumno.toString());

                }
            }catch (Exception e){
                showMessage("Error", "Error en la carga de datos");
            }

        } else if(view == buttonVolver){
            Intent i = new Intent(this, SecondActivity.class );
            startActivity(i);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
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
