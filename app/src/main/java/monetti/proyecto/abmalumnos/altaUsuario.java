package monetti.proyecto.abmalumnos;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class altaUsuario extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    EditText editNombre, editApellido, editNombreUsuario, editDni, editDireccion, editCorreo, editContrasenia, editContrasenia2;
    Spinner spinnerNacionalidad, spinnerCarreraGrado, spinnerLocalidadResidencia, spinnerProvinciaResidencia;
    Button buttonGuardar, buttonVolver;
    SQLiteDatabase db;
    TextView txtDni;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta_usuario);

        //Con esta linea aseguramos que nos muestre el mensaje de error sin que se cierre la aplicacion
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        editDni             = (EditText)findViewById(R.id.editTextDni);
        setOnFocusChangeListener(editDni);

        editNombre          = (EditText)findViewById(R.id.nombre);
        setOnFocusChangeListener(editNombre);

        editApellido        = (EditText)findViewById(R.id.apellido);
        setOnFocusChangeListener(editApellido);

        editNombreUsuario   = (EditText)findViewById(R.id.nombreUsuario);
        setOnFocusChangeListener(editNombreUsuario);

        editCorreo          = (EditText)findViewById(R.id.correo);

        editContrasenia     = (EditText)findViewById(R.id.contrasenia);

        editContrasenia2    = (EditText)findViewById(R.id.contrasenia2);
        editDireccion       = (EditText)findViewById(R.id.direccion);

        buttonGuardar       = (Button) findViewById(R.id.buttonGuardar);
        buttonVolver        = (Button) findViewById(R.id.buttonVolver) ;
        //Se le indica al boton guardar que este escuchando en la activity designada
        buttonGuardar.setOnClickListener(this);
        buttonVolver.setOnClickListener(this);

        //Creacion de la BD
        db=openOrCreateDatabase("DBAlumnos", Context.MODE_PRIVATE, null);
        //<<A agregar>>
        // Se podria agregar una linea que si exite la DB DBAlumnos la dropee.
        //Esto se deberia hacer con un boton, cosa de no estar borrando la cache desde el administrador de aplicaciones


        //LLamadas para armar los Spinners;
        spinnerNacionalidad = (Spinner) findViewById(R.id.spinnerPaisOrigen);
        spinnerCarreraGrado = (Spinner) findViewById(R.id.spinnerCarrera);
        spinnerLocalidadResidencia = (Spinner) findViewById(R.id.spinnerLocalidad);
        spinnerProvinciaResidencia = (Spinner) findViewById(R.id.spinnerProvincia);

        SetSpinners setSpinnerNacionalidad = new SetSpinners(this);

        setSpinnerNacionalidad.execute(0);

        SetSpinners setSpinnerProvincia = new SetSpinners(this);

        setSpinnerProvincia.execute(1);

        this.spinnerProvinciaResidencia.setOnItemSelectedListener(this);

        SetSpinners setSpinnerCarrera = new SetSpinners(this);

        setSpinnerCarrera.execute(3);



    }




    //Validacion de los campos
    private void setOnFocusChangeListener(final EditText editText){

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    switch(v.getId()){
                        case R.id.nombre:
                        case R.id.apellido:

                            for (int i = 0 ; i < editText.length(); ++i ){

                                if (!Character.isLetter(editText.getText().charAt(i))){
                                    editText.setError("Solo se pueden ingresar letras");

                                }
                            }
                            validarEspacios(editText);

                            break;

                        case R.id.nombreUsuario:

                            for (int i = 0 ; i < editText.length(); ++i ){
                                if (!Character.isLetterOrDigit(editText.getText().charAt(i))){
                                    editText.setError("Solo se pueden ingresar caracteres alfanumericos");
                                }

                            }

                            validarEspacios(editText);

                           // ValidarExistenciaRegistro validacion = new ValidarExistenciaRegistro(editText.getText());
                            //validacion.execute();

                            break;

                        case R.id.editTextDni:

                            validarEspacios(editText);

                            if (!(editText.getText().length() == 8 || editText.getText().length() == 7)){
                                editText.setError("Formato DNI incorrecto");
                            } else {
                              //  ValidarExistenciaRegistro validacionDNI = new ValidarExistenciaRegistro(editDni.getText());
                              // validacionDNI.execute();
                            }
                            break;

                        case R.id.correo:

                            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

                            Matcher mather = pattern.matcher(editCorreo.getText());

                            if (mather.matches() == false) {
                                editText.setError("El email ingresado es inválido.");
                            }

                            break;
                    }


                    }
                }
            });
        }




    public void validarEspacios (EditText editTextaValidar){

        if  (editTextaValidar.getText().toString().contains(" ")){
            editTextaValidar.setError("El campo no puede contener espacios en blanco");
        }

    }

    public void onItemSelected(AdapterView <? > parent, View view, int pos, long id) {
        switch (parent.getId()) {
            case R.id.spinnerProvincia:
                if (pos == 0){

                }

                else {
                    SetSpinners setSpinnerLocalidad = new SetSpinners(this);
                    setSpinnerLocalidad.execute(2, pos);
                }

                break;

            case R.id.spinnerLocalidad:

                break;
        }
    }
    public void onNothingSelected(AdapterView <? > parent) {}

    public void onClick(View view) {

        if(view  == buttonGuardar) {
            if (comprobarCamposVacios()) {
                showMessage("Error", "Todos los campos deben estar completos");
                //En este mensaje seria mejor mostrar cual o cuales campos no estan completados

            } else {

                try {

                    db.execSQL(armarQueryInsert(editDni, editNombre, editApellido, editNombreUsuario, editCorreo, editContrasenia, spinnerNacionalidad, spinnerProvinciaResidencia, spinnerLocalidadResidencia, editDireccion, spinnerCarreraGrado));
                }catch(Exception e){
                    showMessage("Title","Error en la Query Insert");
                }

                mostrarInformacionGuardada(editDni);
                clearText();
            }
        }else if(view == buttonVolver){
            Intent i = new Intent(this, MainActivity.class );
            startActivity(i);
        }

    }


    public String armarQueryInsert(EditText editDni,EditText editNombre,EditText editApellido,EditText editNombreUsuario,EditText editCorreo, EditText editContrasenia, Spinner spinnerPaisOrigen, Spinner spinnerProvincia, Spinner spinnerLocalidad, EditText editDireccion, Spinner spinnerCarrera ){

        String queryInsert = "INSERT INTO alumno VALUES('" + editDni.getText() + "','" + editNombre.getText() + "','" + editApellido.getText() + "','" + editNombreUsuario.getText() + "','" + editCorreo.getText() + "','" + editContrasenia.getText() + "','" + spinnerPaisOrigen.getSelectedItem() + "','" +
                spinnerProvincia.getSelectedItem() + "','" + spinnerLocalidad.getSelectedItem() + "','" + editDireccion.getText() + "','" + spinnerCarrera.getSelectedItem() + "');";
        showMessage("Query construida", queryInsert);
        return queryInsert;
    }

    public void mostrarInformacionGuardada(EditText editTextDni){
        Cursor c = db.rawQuery("SELECT * FROM alumno WHERE dni ='" + editTextDni.getText()+"'",null);
        if(c.getCount() == 0){
            showMessage("Error","No se encontro ningun estudiante");
            return;
        }

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
            bufferAlumno.append("Dirección: " + c.getString(9) + "\n");
            bufferAlumno.append("Carrera: " + c.getString(10) + "\n");
            bufferAlumno.append("___________________" + "\n");
        }
        showMessage("Alumno ingresado", bufferAlumno.toString());


    }


    public boolean comprobarCamposVacios(){
        boolean result =    editNombre.getText().toString().trim().length() == 0 ||
                            editApellido.getText().toString().trim().length() == 0 ||
                            editNombreUsuario.getText().toString().trim().length() == 0 ||
                            editDni.getText().toString().trim().length() == 0 ||
                            editDireccion.getText().toString().trim().length() == 0 ||
                            editCorreo.getText().toString().trim().length() == 0 ||
                            editContrasenia.getText().toString().trim().length() == 0 ||
                            editContrasenia2.getText().toString().trim().length() == 0 ||
                            spinnerNacionalidad.getSelectedItemPosition() == 0 ||
                            spinnerProvinciaResidencia.getSelectedItemPosition() ==0 ||
                            spinnerLocalidadResidencia.getSelectedItemPosition() == 0 ||
                            spinnerCarreraGrado.getSelectedItemPosition() == 0;

        return result;
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }




    public void clearText(){
        editDni.setText("");
        editNombre.setText("");
        editApellido.setText("");
        editNombreUsuario.setText("");
        editCorreo.setText("");
        editContrasenia.setText("");
        editContrasenia2.setText("");
        editDireccion.setText("");
        spinnerNacionalidad.setSelection(0);
        spinnerProvinciaResidencia.setSelection(0);
        spinnerLocalidadResidencia.setSelection(0);
        spinnerCarreraGrado.setSelection(0);
        editNombre.requestFocus();
    }


    //Armado de Spinners mediante hilo
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


    private class ValidarExistenciaRegistro extends AsyncTask<Integer, Void, Cursor> {

        Editable campo;
        Cursor cursorResult;

        ValidarExistenciaRegistro(Editable contenidoCampo){
            campo = contenidoCampo;
        }

        @Override
        protected Cursor doInBackground(Integer... params) {

            if (params[0]==0) {
                Cursor c = db.rawQuery("SELECT * FROM alumno WHERE dni ='" + campo +"'",null);
                cursorResult=c;
            }

            if (params[0]==1) {
                Cursor c = db.rawQuery("SELECT * FROM alumno WHERE dni = '" + campo + "'", null);
                cursorResult=c;
            }

            return cursorResult;

        }

        protected void onPostExecute(Cursor c) {

            if (!(c.getCount()==0)) {
                editNombreUsuario.setError("El nombre de usuario ya existe");
            }
        }
    }

}



        /*
        Este bloque es para tener los nombres de las variables en una lista, me ha servido bastante tenerlo asi para copypastear jaja
        editDni
        editNombre
        editApellido
        editNombreUsuario
        editCorreo
        editContrasenia
        editContrasenia2
        spinnerPaisOrigen
        spinnerProvincia
        editLocalidad
        editDireccion
        spinnerCarrera
        */