package monetti.proyecto.abmalumnos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class verUsuario extends AppCompatActivity implements View.OnClickListener{

    Button buttonVolver, buttonVer;
    EditText editDni;
    TextView tvInformacionAlumno;
    SQLiteDatabase db;
    ArrayList tipoUsuarioCod;
    Editable nombreU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.ver_usuario);

        editDni             = (EditText) findViewById(R.id.dni);
        tvInformacionAlumno = (TextView) findViewById(R.id.tvInformacionAlumno);
        buttonVer           = (Button) findViewById(R.id.buttonVer);
        buttonVolver        = (Button) findViewById(R.id.buttonVolver);

        ArrayList tipoUsuarioCod = ComunicadorClases.getObject();
        nombreU = (Editable) tipoUsuarioCod.get(1);
        buttonVer.setOnClickListener(this);
        buttonVolver.setOnClickListener(this);

        db=openOrCreateDatabase("DBAlumnos", Context.MODE_PRIVATE, null);

        if (tipoUsuarioCod.get(0) == "usrAlm"){
            editDni.setVisibility(View.INVISIBLE);
            buttonVer.setVisibility(View.INVISIBLE);
            getInformacionGuardada(nombreU);
        }
    }

    public void onClick(View view){
        if (view == buttonVer){
            if (editDni.getText().toString().trim().length() == 0){
                showMessage("Error","El campo DNI no puede estar vacio.");
            } else {;
                getInformacionGuardada(editDni.getText());
            }
        }
        if (view == buttonVolver){
            Intent i = new Intent(this, SecondActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }
    }

    public void getInformacionGuardada(Editable editTextUsuario){

        if (tipoUsuarioCod.get(0) == "usrAlm"){
            Cursor c = db.rawQuery("SELECT * FROM alumno WHERE nombreUsuario ='" + editTextUsuario.toString().toUpperCase()+"'",null);
            mostrarInformacionGuardada(c);
        }else {
            Cursor c = db.rawQuery("SELECT * FROM alumno WHERE dni ='" + editTextUsuario.toString() + "'", null);
            if (c.getCount() == 0) {
                showMessage("Error", "No se encontro ningun estudiante con dni " + editTextUsuario.toString());
                return;
            }

            mostrarInformacionGuardada(c);
        }

    }

    public void mostrarInformacionGuardada(Cursor c){
        StringBuffer bufferAlumno = new StringBuffer();
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
            bufferAlumno.append("Dirección: " + "\n\tCalle: " + c.getString(9) + "\tN°: " + c.getString(10) + "\n");
            bufferAlumno.append("Carrera: " + c.getString(11) + "\n");
        }

        tvInformacionAlumno.setText(bufferAlumno.toString());
        editDni.setText("");
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
