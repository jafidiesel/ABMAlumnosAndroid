package monetti.proyecto.abmalumnos;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ModificarDatosAlumno extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Spinner spinnerNacionalidad, spinnerCarreraGrado, spinnerLocalidadResidencia, spinnerProvinciaResidencia;
    private View contenedorApellido, contenedorCalle, contenedorNumeracion;
    TextView dniAlumno;
    EditText nombreAlumno,  apellidoAlumno, calleUpdate, numeracionUpdate;
    RadioButton editarDireccion;
    SQLiteDatabase db;
    String nombreU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_datos_alumno);

        db=openOrCreateDatabase("DBAlumnos", Context.MODE_PRIVATE, null);

        ArrayList tipoUsuarioCod = ComunicadorClases.getObject();
        nombreU = (String) tipoUsuarioCod.get(1);
        nombreAlumno = (EditText) findViewById(R.id.nombre);
        nombreAlumno.setEnabled(false);
        apellidoAlumno = (EditText) findViewById(R.id.apellido);
        dniAlumno = (TextView) findViewById(R.id.textDni);
        editarDireccion = (RadioButton) findViewById(R.id.radioButtonEditDireccion);
        contenedorCalle =  (View) findViewById(R.id.LinearLayoutCalle);
        contenedorCalle.setVisibility(View.INVISIBLE);
        contenedorNumeracion = (View) findViewById(R.id.LinearLayoutNumeracion);
        contenedorNumeracion.setVisibility(View.INVISIBLE);
        contenedorApellido = (View) findViewById(R.id.LinearLayoutApellido);
        calleUpdate = (EditText) findViewById(R.id.editDireccionCalle);
        numeracionUpdate = (EditText) findViewById(R.id.editDireccionNumeracion);

        if (tipoUsuarioCod.get(0) == "usrAlm"){
            GetDatosUsuario data = new GetDatosUsuario(nombreU);
            data.execute(0);
        }

        spinnerNacionalidad = (Spinner) findViewById(R.id.spinnerPaisOrigen);
        spinnerCarreraGrado = (Spinner) findViewById(R.id.spinnerCarrera);
        spinnerLocalidadResidencia = (Spinner) findViewById(R.id.spinnerLocalidad);
        spinnerProvinciaResidencia = (Spinner) findViewById(R.id.spinnerProvincia);

        SetSpinnersM setSpinnerNacionalidad = new SetSpinnersM(this);
        setSpinnerNacionalidad.execute(0);
        SetSpinnersM setSpinnerProvincia = new SetSpinnersM(this);
        setSpinnerProvincia.execute(1);

        this.spinnerProvinciaResidencia.setOnItemSelectedListener(this);
        SetSpinnersM setSpinnerCarrera = new SetSpinnersM(this);
        setSpinnerCarrera.execute(3);

    }

    public void onItemSelected(AdapterView<? > parent, View view, int pos, long id) {
        switch (parent.getId()) {
            case R.id.spinnerProvincia:
                if (pos == 0){

                }

                else {
                   SetSpinnersM setSpinnerLocalidad = new SetSpinnersM(this); //Carga del SpinnerLocalidad de acuerdo a la provincia selecionada
                    setSpinnerLocalidad.execute(2, pos);
                }

                break;

            case R.id.spinnerLocalidad:

                break;
        }
    }

    public void onNothingSelected(AdapterView <? > parent) {}

    @Override
    public void onClick(View v) {

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
                    dniAlumno.setText(cSQL.getString(0));
                    nombreAlumno.setText(cSQL.getString(1));
                    apellidoAlumno.setText(cSQL.getString(2));

                }
            }



        }
    }


    public void onRadioButtonClicked(View view) {
        boolean marcado = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonEditDireccion:
                if (marcado) {
                    contenedorCalle.setVisibility(View.VISIBLE);
                    contenedorNumeracion.setVisibility(View.VISIBLE);
                    calleUpdate.requestFocus();

                }
                break;

            }
    }

    private void mostrarParticular(boolean b) {
        contenedorApellido.setVisibility(b ? View.VISIBLE: View.GONE);
        //contenedorCorporativo.setVisibility(b ? View.GONE : View.VISIBLE);
    }

    private class SetSpinnersM extends AsyncTask<Integer, Void, ArrayList> {

        ArrayList objectArrayList = new ArrayList();
        Context context;
        SetSpinnersM(Context context) {
            this.context = context;
        }

        @Override
        protected ArrayList doInBackground(Integer... params) {

            if (params[0] == 0) {
                ArrayAdapter<CharSequence> adapterNacionalidad = ArrayAdapter.createFromResource(context, R.array.setNacionalidad, android.R.layout.simple_spinner_item);
                adapterNacionalidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                objectArrayList.add(0, params[0]);
                objectArrayList.add(1, adapterNacionalidad);

            }

            if (params[0] == 1) {
                ArrayAdapter<CharSequence> adapterProvincia = ArrayAdapter.createFromResource(context, R.array.setProvincia, android.R.layout.simple_spinner_item);
                adapterProvincia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                objectArrayList.add(0, params[0]);
                objectArrayList.add(1, adapterProvincia);

            }

            if (params[0]==2){
                TypedArray arrayCiudades = getResources().obtainTypedArray(R.array.parProvinciaLocalidad);
                CharSequence[] ciudadLista = arrayCiudades.getTextArray(params[1]);
                arrayCiudades.recycle();

                ArrayAdapter<CharSequence> adapterLocalidad = new ArrayAdapter<CharSequence>(context, android.R.layout.simple_spinner_item,android.R.id.text1, ciudadLista);
                adapterLocalidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                objectArrayList.add(0, params[0]);
                objectArrayList.add(1, adapterLocalidad);

            }

            if (params[0] == 3) {

                ArrayAdapter<CharSequence> adapterCarrera= ArrayAdapter.createFromResource(context, R.array.setCarrera, android.R.layout.simple_spinner_item);
                adapterCarrera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                objectArrayList.add(0, params[0]);
                objectArrayList.add(1, adapterCarrera);

            }

            return objectArrayList;
        }

        protected void onPostExecute(ArrayList result) {

            if ((Integer) result.get(0) == 0) {
                spinnerNacionalidad.setAdapter((SpinnerAdapter) result.get(1));
            }

            if ((Integer) result.get(0) == 1) {
                spinnerProvinciaResidencia.setAdapter((SpinnerAdapter) result.get(1));
            }

            if ((Integer) result.get(0) == 2) {
                spinnerLocalidadResidencia.setAdapter((SpinnerAdapter) result.get(1));
            }

            if ((Integer) result.get(0) == 3) {
                spinnerCarreraGrado.setAdapter((SpinnerAdapter) result.get(1));
            }
        }
    }





}
