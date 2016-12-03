package monetti.proyecto.abmalumnos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog.Builder;


public class bajaUsuario extends AppCompatActivity implements View.OnClickListener{

    Button buttonBajar, buttonVolver;
    EditText editTextDni;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.baja_usuario);

        editTextDni = (EditText) findViewById(R.id.dni);
        buttonBajar  = (Button) findViewById(R.id.buttonBajar);
        buttonVolver = (Button) findViewById(R.id.buttonVolver) ;

        buttonBajar.setOnClickListener(this);
        buttonVolver.setOnClickListener(this);

        db=openOrCreateDatabase("DBAlumnos", Context.MODE_PRIVATE, null);

    }


    public void onClick(View view) {

        if (view == buttonBajar) {
            if (editTextDni.getText().toString().trim().length() == 0) {
                showMessage("Error", "Por favor, ingrese DNI");
                return;
            }

            Cursor c = db.rawQuery("SELECT * FROM DBAlumnos WHERE dni = '" + editTextDni.getText() + "'", null);

            if (c.moveToFirst()) {
                db.execSQL("DELETE FROM DBAlumnos WHERE dni='" + editTextDni.getText() + "'");
                showMessage("Exito", "Registro borrado");
            } else {
                showMessage("Error", "DNI no valido");
            }

        }
        if (view == buttonVolver){
            Intent i = new Intent(this, MainActivity.class );
            startActivity(i);
        }
    }

    public void showMessage(String title, String message) {
        Builder builder = new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
