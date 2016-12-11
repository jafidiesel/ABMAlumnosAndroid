package monetti.proyecto.abmalumnos;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
    }


    public void onClick(View view){
        if (view == buttonDeleteAll) {
        /*try {
            db.execSQL("DELETE FROM alumno WHERE 1");
            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
            showMessage("Drop Table","Datos de tabla alumno eliminados exitosamente");
        } catch (Exception e){
            showMessage("Error","Error eliminando la DB");
        }*/
        }else if(view == buttonAddRandom){
            // Metodo en el que se agregan datos random y se lo muestra por pantalla
        } else if(view == buttonVolver){
            Intent i = new Intent(this, MainActivity.class );
            startActivity(i);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }
    }
}
