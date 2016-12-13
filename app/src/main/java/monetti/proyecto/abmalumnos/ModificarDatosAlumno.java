package monetti.proyecto.abmalumnos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class ModificarDatosAlumno extends AppCompatActivity {

    TextView nombreAlumno, apellidoAlumno, dniAlumno;
    SQLiteDatabase db;
    String nombreU;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_datos_alumno);

        db=openOrCreateDatabase("DBAlumnos", Context.MODE_PRIVATE, null);

        ArrayList tipoUsuarioCod = ComunicadorClases.getObject();
        nombreU = (String) tipoUsuarioCod.get(1);
        nombreAlumno = (TextView) findViewById(R.id.nombre);
        apellidoAlumno = (TextView) findViewById(R.id.apellido);
        dniAlumno = (TextView) findViewById(R.id.textDni);

        if (tipoUsuarioCod.get(0) == "usrAlm"){
            GetDatosUsuario data = new GetDatosUsuario(nombreU);
            data.execute(0);
        }

    }

    private class GetDatosUsuario extends AsyncTask<Integer, Void, ArrayList> {

        String campo;
        ArrayList resultIndex = new ArrayList();
        Cursor cSQL;

        GetDatosUsuario(String contenidoCampo) {
            campo = contenidoCampo;
        }

        @Override
        protected ArrayList doInBackground(Integer... params) {

            if (params[0] == 0) {
                Cursor c = db.rawQuery("SELECT * FROM alumno WHERE nombreUsuario ='" + campo.toString().toUpperCase() + "'", null);
                resultIndex.add(0,params[0]);
                resultIndex.add(1, c);
            }

            if (params[0] == 1) {
                Cursor c = db.rawQuery("SELECT * FROM alumno WHERE dni = '" + campo.toString() + "'", null);
                resultIndex.add(0,params[0]);
                resultIndex.add(1, c);;
            }

            return resultIndex;

        }

        protected void onPostExecute(ArrayList result) {

            if ((Integer) result.get(0) == 0) {
                cSQL = (Cursor) result.get(1);
                while(cSQL.moveToNext()) {
                    nombreAlumno.setText(cSQL.getString(1));
                    apellidoAlumno.setText(cSQL.getString(2));
                    dniAlumno.setText(cSQL.getString(0));
                }
            }



        }
    }
}
