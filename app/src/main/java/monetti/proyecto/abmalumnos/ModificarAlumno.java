package monetti.proyecto.abmalumnos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

    /*
    * Esta clase le permite al administrador buscar el alumno para modificar sus datos.
    * */

public class ModificarAlumno extends AppCompatActivity implements View.OnClickListener{

    Button buttonAceptar,buttonVolver;
    EditText dniUsuario;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_alumno);

        buttonVolver = (Button) findViewById(R.id.buttonVolver);
        buttonAceptar = (Button) findViewById(R.id.buttonAceptar);
        dniUsuario = (EditText) findViewById(R.id.editTextDniUsuario);

        buttonAceptar.setOnClickListener(this);
        buttonVolver.setOnClickListener(this);
        db=openOrCreateDatabase("DBAlumnos", Context.MODE_PRIVATE, null);

    }



    @Override
    public void onClick(View v) {
        if (v == buttonAceptar) {
            //Verifica la existencia del alumno
            ValidarExistenciaRegistro dataUsuario = new ValidarExistenciaRegistro(dniUsuario.getText(), this);
            dataUsuario.execute(0);
        }
        if (v==buttonVolver){
            ComunicadorClases.setOpcion("Alta");
            Intent i = new Intent(this,SecondActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }
    }


    private class ValidarExistenciaRegistro extends AsyncTask<Integer, Void, ArrayList> {
        //Validacion mediante Hilo

        Editable campo;
        ArrayList resultIndex = new ArrayList();
        Cursor cSQL;
        Context context;

        ValidarExistenciaRegistro(Editable contenidoCampo, Context context) {
            campo = contenidoCampo;
            this.context = context;
        }

        @Override
        protected ArrayList doInBackground(Integer... params) {

            if (params[0] == 0) {
                Cursor c = db.rawQuery("SELECT * FROM alumno WHERE dni = '" + campo.toString() + "'", null);
                resultIndex.add(0,params[0]);
                resultIndex.add(1, c);;
            }


            return resultIndex;

        }

        protected void onPostExecute(ArrayList result) {
                if (((Integer) result.get(0) == 0)) {
                    cSQL = (Cursor) result.get(1);
                    if (cSQL.getCount() == 0) {
                        showMessage("Error", "El usuario ingresado no existe");
                    } else{
                        ComunicadorClases.setDniUsuario(dniUsuario.getText());
                        Intent i = new Intent(context, altaUsuario.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    }

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
