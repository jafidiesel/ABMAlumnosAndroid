package monetti.proyecto.abmalumnos;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class opciones extends AppCompatActivity implements View.OnClickListener{

    Button buttonDeleteAll, buttonAddRandom, buttonVolver;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_opciones);
        buttonDeleteAll      = (Button) findViewById(R.id.buttonDeleteAll);
        buttonAddRandom      = (Button) findViewById(R.id.buttonAddRandom);
        buttonVolver      = (Button) findViewById(R.id.buttonVolver);

        db=openOrCreateDatabase("DBAlumnos", Context.MODE_PRIVATE, null);

        buttonAddRandom.setOnClickListener(this);
        buttonDeleteAll.setOnClickListener(this);
        buttonVolver.setOnClickListener(this);
    }


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

            try {
                //Falta la comprobacion que ya ha sido ingresado el valor
                String queryInsert = "INSERT INTO alumno VALUES('1234567','NOMBREPRUEBA','APELLIDOPRUEBA','NOMBREUSUARIOPRUEBA','Correo@prueba.com','contrasenia','Argentina','Mendoza','Guaymallen','CALLEPRUEBA','123','Ing. en Sistemas de Información');";
                showMessage("Query construida",queryInsert);
                db.execSQL(queryInsert);
            }catch(Exception e){
                showMessage("Title","Error en la Query Insert");
            }


        } else if(view == buttonVolver){
            Intent i = new Intent(this, MainActivity.class );
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
