package com.example.root.hilosspinners;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner nacionalidad, carreraGrado, localidadResidencia, provinciaResidencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nacionalidad = (Spinner) findViewById(R.id.spinnerNacionalidad);
        carreraGrado = (Spinner) findViewById(R.id.spinnerCarrera);
        localidadResidencia = (Spinner) findViewById(R.id.spinnerLocalidad);
        provinciaResidencia = (Spinner) findViewById(R.id.spinnerProvincia);

        SetSpinners setSpinnerNacionalidad = new SetSpinners(this);

        setSpinnerNacionalidad.execute(0);

        SetSpinners setSpinnerProvincia = new SetSpinners(this);

        setSpinnerProvincia.execute(1);

        this.provinciaResidencia.setOnItemSelectedListener(this);

        SetSpinners setSpinnerCarrera = new SetSpinners(this);

        setSpinnerCarrera.execute(3);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.spinnerProvincia:
                if (position == 0){

                }

                else {
                    SetSpinners setSpinnerLocalidad = new SetSpinners(this);
                    setSpinnerLocalidad.execute(2, position);
                }

                break;

            case R.id.spinnerLocalidad:

                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    private class SetSpinners extends AsyncTask<Integer, Void, ArrayList> {

        ArrayList objectArrayList = new ArrayList();

        Context context;

        SetSpinners(Context context) {
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

                // Specify the layout to use when the list of choices appears
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

                nacionalidad.setAdapter((SpinnerAdapter) result.get(1));

            }

            if ((Integer) result.get(0) == 1) {

                provinciaResidencia.setAdapter((SpinnerAdapter) result.get(1));

            }

            if ((Integer) result.get(0) == 2) {

                localidadResidencia.setAdapter((SpinnerAdapter) result.get(1));

            }

            if ((Integer) result.get(0) == 3) {

                carreraGrado.setAdapter((SpinnerAdapter) result.get(1));

            }
        }
    }
}
