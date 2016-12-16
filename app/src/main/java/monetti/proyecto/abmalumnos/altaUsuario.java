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
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;




public class altaUsuario extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    EditText dniUsuario,editNombre, editApellido, editNombreUsuario, editDni, editDireccionCalle, editDireccionNumeracion, editCorreo, editContrasenia, editContrasenia2;
    Spinner spinnerNacionalidad, spinnerCarreraGrado, spinnerLocalidadResidencia, spinnerProvinciaResidencia;
    Button buttonGuardar, buttonVolver;
    SQLiteDatabase db;
    Boolean[] saveFlag = {true,true,true,true,true,true,true,true,true};
    String tipoUsuario = "usrAlm";
    View contenedorCalle, contenedorNumeracion,contenedorDatos;
    String opcion;
    Editable nombreU;
    RadioButton editarNombre,editarApellido,editarDni,editarUsuario,editarPassword, editarCorreo,editarProvincia, editarNacionalidad, editarLocalidad, editarCarrera,editarDireccion;
    TextView titulo, tvEditarPassword;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta_usuario);

        //Con esta linea aseguramos que nos muestre el mensaje de error sin que se cierre la aplicacion
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        titulo = (TextView)findViewById(R.id.title1);

        editarNombre = (RadioButton)findViewById(R.id.radioButtonNombre);
        editarApellido = (RadioButton) findViewById(R.id.radioButtonApellido);
        editarDni = (RadioButton) findViewById(R.id.radioButtonDni);
        editarUsuario = (RadioButton) findViewById(R.id.radioButtonUsuario);
        editarPassword = (RadioButton) findViewById(R.id.radioButtonPassword);
        editarNacionalidad = (RadioButton) findViewById(R.id.radioButtonNacionalidad);
        editarProvincia = (RadioButton)findViewById(R.id.radioButtonProvincia);
        editarLocalidad = (RadioButton) findViewById(R.id.radioButtonLocalidad);
        editarCorreo = (RadioButton)findViewById(R.id.radioButtonCorreo);
        editarCarrera = (RadioButton) findViewById(R.id.radioButtonCarrera);
        editarDireccion = (RadioButton) findViewById(R.id.radioButtonDomicilio);

        editDni             = (EditText)findViewById(R.id.editTextDni);
        setOnFocusChangeListener(editDni);
        editNombre          = (EditText)findViewById(R.id.nombre);
        setOnFocusChangeListener(editNombre);
        editApellido        = (EditText)findViewById(R.id.apellido);
        setOnFocusChangeListener(editApellido);
        editNombreUsuario   = (EditText)findViewById(R.id.nombreUsuario);
        setOnFocusChangeListener(editNombreUsuario);
        editCorreo          = (EditText)findViewById(R.id.correo);
        setOnFocusChangeListener(editCorreo);
        editContrasenia     = (EditText)findViewById(R.id.contrasenia);
        setOnFocusChangeListener(editContrasenia);
        editContrasenia2    = (EditText)findViewById(R.id.contrasenia2);
        setOnFocusChangeListener(editContrasenia2);
        editDireccionCalle       = (EditText)findViewById(R.id.editDireccionCalle);
        setOnFocusChangeListener(editDireccionCalle);
        editDireccionNumeracion = (EditText) findViewById(R.id.editDireccionNumeracion);
        setOnFocusChangeListener(editDireccionNumeracion);


        buttonGuardar       = (Button) findViewById(R.id.buttonGuardar);
        buttonVolver        = (Button) findViewById(R.id.buttonVolver) ;
        //Se le indica al boton guardar que este escuchando en la activity designada

        buttonGuardar.setOnClickListener(this);
        buttonVolver.setOnClickListener(this);

        dniUsuario = (EditText) findViewById(R.id.editTextDniUsuario);
        tvEditarPassword = (TextView)findViewById(R.id.tvRepetirPassword);
        contenedorCalle = (View) findViewById(R.id.contenedorCalle);
        contenedorNumeracion = (View) findViewById(R.id.contenedorNumeracion);

        contenedorDatos = (View) findViewById(R.id.scrollView1);
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

        ArrayList tipoUsuarioCod = ComunicadorClases.getObject();
        nombreU = (Editable) tipoUsuarioCod.get(1);
        opcion = ComunicadorClases.getOpcion();


        if (opcion == "Modificar"){
            titulo.setText("Modificar");
            if (tipoUsuarioCod.get(0) == "usrAlm"){
                ValidarExistenciaRegistro data = new ValidarExistenciaRegistro(nombreU);
                data.execute(2);

                editarNombre.setVisibility(View.INVISIBLE);
                editarApellido.setVisibility(View.INVISIBLE);
                editarDni.setVisibility(View.INVISIBLE);



            }else if (tipoUsuarioCod.get(0) == "usrAdm"){
                Editable dniUsuarioIngresado = ComunicadorClases.getDniUsuario();
                //Primero debe ingresar en algun lado el nombre del usurio que desae modificar
                ValidarExistenciaRegistro dataUsr = new ValidarExistenciaRegistro(dniUsuarioIngresado);
                dataUsr.execute(3);


            }
        } else if (opcion == "Alta"){
            titulo.setText("Alta Alumno");
            editarNombre.setVisibility(View.INVISIBLE);
            editarApellido.setVisibility(View.INVISIBLE);
            editarDni.setVisibility(View.INVISIBLE);
            editarUsuario.setVisibility(View.INVISIBLE);
            editarPassword.setVisibility(View.INVISIBLE);
            editarNacionalidad.setVisibility(View.INVISIBLE);
            editarProvincia.setVisibility(View.INVISIBLE);
            editarLocalidad.setVisibility(View.INVISIBLE);
            editarCorreo.setVisibility(View.INVISIBLE);
            editarCarrera.setVisibility(View.INVISIBLE);
            editarDireccion.setVisibility(View.INVISIBLE);

        }

    }

    //Validacion de los campos al cambiar el foco de campo
    private void setOnFocusChangeListener(final EditText editText){

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus) {
                    switch(v.getId()){

                        case R.id.nombre:

                            validarString(editText,0);
                            break;

                        case R.id.apellido:

                            validarString(editText,1);

                            break;

                        case R.id.nombreUsuario:

                            validarNombreUsuario(editText,2);

                            break;

                        case R.id.editTextDni:
                            if (!(editText.getText().length() == 8 || editText.getText().length() == 7)){
                                editText.setError("Formato DNI incorrecto");
                                changeSaveFlag(false,3);
                            } else {
                              ValidarExistenciaRegistro validacionDNI = new ValidarExistenciaRegistro(editDni.getText());
                              validacionDNI.execute(1);

                            }
                            break;

                        case R.id.correo:

                            if (!Patterns.EMAIL_ADDRESS.matcher(editCorreo.getText().toString()).matches()) {
                                editText.setError("El email ingresado no es válido. Example@example.com");
                                changeSaveFlag(false,5);
                            } else{
                                editText.setError(null);
                                changeSaveFlag(true,5);
                            }

                            break;

                        case R.id.contrasenia:
                            if (editText.getText().length()<8 ){
                                editText.setError("La contraseña debe contener 8 o más caracteres");
                                changeSaveFlag(false,6);
                            }else{
                                editText.setError(null);
                                changeSaveFlag(true,6);
                            }

                            break;
                        case R.id.contrasenia2:

                            if (!(editText.getText().toString().equals(editContrasenia.getText().toString()))){
                                editText.setError("Las contraseñas no coinciden");
                                changeSaveFlag(false,7);
                            }else{
                                editText.setError(null);
                                changeSaveFlag(true,7);
                            }

                            break;

                        case R.id.editDireccionCalle:

                            validarString(editText,8);

                            break;
                        case R.id.editDireccionNumeracion:

                            break;
                    }


                }
            }
        });
    }



    //Validacion de los campos String
   public void validarString (EditText editTextaValidar, int banderaN){

       Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(editTextaValidar.getText().toString()).matches()){
            editTextaValidar.setError("Solo se pueden ingresar letras");
            changeSaveFlag(false,banderaN);
        }else{
            editTextaValidar.setError(null);
            changeSaveFlag(true, 0);

        };

    }

    public void validarNombreUsuario (EditText editTextaValidar, int banderaN){

        for (int i = 0 ; i < editTextaValidar.length(); ++i ){
            if (!Character.isLetterOrDigit(editTextaValidar.getText().toString().charAt(i))){
                editTextaValidar.setError("Solo se pueden ingresar caracteres alfanumericos");
                changeSaveFlag(false,2);
            } else{
                editTextaValidar.setError(null);
                changeSaveFlag(true,2);
            }

        }

        ValidarExistenciaRegistro validacion = new ValidarExistenciaRegistro(editTextaValidar.getText());
        validacion.execute(0);

    }



    public void onItemSelected(AdapterView <? > parent, View view, int pos, long id) {
        switch (parent.getId()) {
            case R.id.spinnerProvincia:
                if (pos == 0){

                }

                else {
                    SetSpinners setSpinnerLocalidad = new SetSpinners(this); //Carga del SpinnerLocalidad de acuerdo a la provincia selecionada
                    setSpinnerLocalidad.execute(2, pos);
                }

                break;

            case R.id.spinnerLocalidad:

                break;
        }
    }

    public void onNothingSelected(AdapterView <? > parent) {}

    public void onClick(View view) {

        if (opcion == "Modificar"){
            validarString(editNombre,0);
            validarString(editApellido,1);
            validarNombreUsuario(editNombreUsuario,2);
        }

        if(view  == buttonGuardar) {
            if (comprobarCamposVacios()) {
                showMessage("Error", "Todos los campos deben estar completos");

                //En este mensaje seria mejor mostrar cual o cuales campos no estan completados

            }else if (recorrerFlags() && opcion=="Modificar"){
                showMessage("Exito", "RegistroAccedido");

                Cursor c = db.rawQuery("SELECT * FROM alumno WHERE dni = '"+ editDni.getText()+"'", null);
                if (c.moveToFirst()) {
                    db.execSQL("UPDATE alumno SET dni= '" + editDni.getText()+ "',nombre='"+ editNombre.getText().toString().toUpperCase()+"',apellido='"  + editApellido.getText().toString().toUpperCase() + "', nombreUsuario='" + editNombreUsuario.getText().toString().toUpperCase()+ "',correo='"+ editCorreo.getText().toString().toUpperCase()+"',password='"+ editContrasenia.getText()+"',paisOrigen='"+ spinnerNacionalidad.getSelectedItem().toString()+"',provincia='"+ spinnerProvinciaResidencia.getSelectedItem()+"',localidad='"+ spinnerLocalidadResidencia.getSelectedItem()+"',direccionCalle='"+editDireccionCalle.getText().toString().toUpperCase()+"',numeracion='"+ editDireccionNumeracion.getText()+"',carrera='"+spinnerCarreraGrado.getSelectedItem()+"',tipoUsuario='"+ tipoUsuario+ "' WHERE dni='" + editDni.getText() + "'");
                    Toast.makeText(this,"Usuario actualizado con exito",Toast.LENGTH_LONG).show();
                    ComunicadorClases.setOpcion("Alta");
                    Intent i = new Intent(this, SecondActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.right_in, R.anim.right_out);
                }
            }else if(!recorrerFlags()){
                showMessage("Error", "Los campos estan ingresados incorrectamente.");
            } else if(recorrerFlags()){

                try {

                    db.execSQL(armarQueryInsert(editDni, editNombre, editApellido, editNombreUsuario, editCorreo, editContrasenia, spinnerNacionalidad, spinnerProvinciaResidencia, spinnerLocalidadResidencia, editDireccionCalle, editDireccionNumeracion, spinnerCarreraGrado,tipoUsuario));
                }catch(Exception e){
                    showMessage("Title","Error en la Query Insert");
                }

                mostrarInformacionGuardada(editDni);
                clearText();
            }
        }else if(view == buttonVolver){
            ComunicadorClases.setOpcion("Alta");
            Intent i = new Intent(this, SecondActivity.class );
            startActivity(i);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }


    }


    public String armarQueryInsert(EditText editDni,EditText editNombre,EditText editApellido,EditText editNombreUsuario,EditText editCorreo, EditText editContrasenia, Spinner spinnerPaisOrigen, Spinner spinnerProvincia, Spinner spinnerLocalidad, EditText editDireccionCalle, EditText editDireccionNumeracion, Spinner spinnerCarrera, String tipoUsuario ){

        String queryInsert = "INSERT INTO alumno VALUES('" + editDni.getText() + "','" + editNombre.getText().toString().toUpperCase() + "','" + editApellido.getText().toString().toUpperCase() + "','" + editNombreUsuario.getText().toString().toUpperCase()+ "','" + editCorreo.getText().toString().toUpperCase() + "','" + editContrasenia.getText().toString() + "','" + spinnerPaisOrigen.getSelectedItem() + "','" +
                spinnerProvincia.getSelectedItem() + "','" + spinnerLocalidad.getSelectedItem() + "','" + editDireccionCalle.getText().toString().toUpperCase()+ "','" + editDireccionNumeracion.getText() + "','" + spinnerCarrera.getSelectedItem() +"','" + tipoUsuario+ "');";
        showMessage("Query construida", queryInsert);
        return queryInsert;
    }

   /* public String armarQueryUpdate(EditText editDni,EditText editNombre,EditText editApellido,EditText editNombreUsuario,EditText editCorreo, EditText editContrasenia, Spinner spinnerPaisOrigen, Spinner spinnerProvincia, Spinner spinnerLocalidad, EditText editDireccionCalle, EditText editDireccionNumeracion, Spinner spinnerCarrera, String tipoUsuario ){

        String queryUpdate ="UPDATE alumno SET('" + editDni.getText() + "','" + editNombre.getText().toString().toUpperCase() + "','" + editApellido.getText().toString().toUpperCase() + "','" + editNombreUsuario.getText().toString().toUpperCase() + "','" + editCorreo.getText().toString().toUpperCase() + "','" + editContrasenia.getText().toString() + "','" + spinnerPaisOrigen.getSelectedItem() + "','" +
                spinnerProvincia.getSelectedItem() + "','" + spinnerLocalidad.getSelectedItem() + "','" + editDireccionCalle.getText().toString().toUpperCase()+ "','" + editDireccionNumeracion.getText() + "','" + spinnerCarrera.getSelectedItem() +"','" + tipoUsuario+ "') WHERE ("+editDni.getText().toString()+"');";

        return queryUpdate;
    }*/

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
            bufferAlumno.append("Dirección: " + "\n\tCalle: "+ c.getString(9) + "\tN°: " + c.getString(10) +"\n");
            bufferAlumno.append("Carrera: " + c.getString(11) + "\n");
            bufferAlumno.append("___________________" + "\n");
        }
        showMessage("Alumno ingresado", bufferAlumno.toString());


    }


    public boolean comprobarCamposVacios(){
        boolean result =    editNombre.getText().toString().trim().length() == 0 ||
                            editApellido.getText().toString().trim().length() == 0 ||
                            editNombreUsuario.getText().toString().trim().length() == 0 ||
                            editDni.getText().toString().trim().length() == 0 ||
                            editDireccionCalle.getText().toString().trim().length() == 0 ||
                            editDireccionNumeracion.getText().toString().trim().length() == 0 ||
                            editCorreo.getText().toString().trim().length() == 0 ||
                            editContrasenia.getText().toString().trim().length() == 0 ||
                            editContrasenia2.getText().toString().trim().length() == 0 ||
                            spinnerNacionalidad.getSelectedItemPosition() == 0 ||
                            spinnerProvinciaResidencia.getSelectedItemPosition() ==0 ||
                            //spinnerLocalidadResidencia.getSelectedItemPosition() == 0 ||
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
        editDireccionCalle.setText("");
        editDireccionNumeracion.setText("");
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




    private class ValidarExistenciaRegistro extends AsyncTask<Integer, Void, ArrayList> {

        Editable campo;
        ArrayList resultIndex = new ArrayList();
        Cursor cSQL;

        ValidarExistenciaRegistro(Editable contenidoCampo) {
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

            if (params[0] == 2) {
                Cursor c = db.rawQuery("SELECT * FROM alumno WHERE nombreUsuario ='" + campo.toString().toUpperCase() + "'", null);
                resultIndex.add(0,params[0]);
                resultIndex.add(1, c);
            }

            if (params[0] == 3) {
                Cursor c = db.rawQuery("SELECT * FROM alumno WHERE dni = '" + campo.toString() + "'", null);
                resultIndex.add(0,params[0]);
                resultIndex.add(1, c);;
            }

            return resultIndex;

        }

        protected void onPostExecute(ArrayList result) {

            if ((Integer) result.get(0) == 0) {
                cSQL = (Cursor) result.get(1);
                if (!(cSQL.getCount() == 0)) {
                    editNombreUsuario.setError("El nombre de usuario ya existe");
                    changeSaveFlag(false,3);
                } else{
                    changeSaveFlag(true,3);
                }
            }

            if ((Integer) result.get(0) == 1) {
                cSQL = (Cursor) result.get(1);
                if (!(cSQL.getCount() == 0)) {
                    editDni.setError("Alumno ya registrado");
                    changeSaveFlag(false,4);
                } else {
                    changeSaveFlag(true,4);
                }
            }

            if (((Integer) result.get(0) == 2)||((Integer) result.get(0) == 3)) {
                    cSQL = (Cursor) result.get(1);
                    while (cSQL.moveToNext()) {
                        editDni.setText(cSQL.getString(0));
                        editNombre.setText(cSQL.getString(1));
                        editApellido.setText(cSQL.getString(2));
                        editNombreUsuario.setText(cSQL.getString(3));
                        editCorreo.setText(cSQL.getString(4));
                        editContrasenia.setText(cSQL.getString(5));
                        editContrasenia2.setText(cSQL.getString(5));
                        editDireccionCalle.setText(cSQL.getString(9));
                        editDireccionNumeracion.setText(cSQL.getString(10));

                        setDatosSpinner(spinnerNacionalidad, cSQL.getString(6));
                        setDatosSpinner(spinnerProvinciaResidencia, cSQL.getString(7)); // Al estar activado el metodo OnItemListener se crea el spinnerLocalidad
                        setDatosSpinner(spinnerCarreraGrado, cSQL.getString(11));

                    }

                    editNombre.setEnabled(false);
                    editApellido.setEnabled(false);
                    editDni.setEnabled(false);
                    editNombreUsuario.setEnabled(false);
                    spinnerNacionalidad.setEnabled(false);;
                    spinnerProvinciaResidencia.setEnabled(false);
                    spinnerLocalidadResidencia.setEnabled(false);
                    spinnerCarreraGrado.setEnabled(false);
                    editCorreo.setEnabled(false);
                    editContrasenia.setVisibility(View.INVISIBLE);
                    tvEditarPassword.setVisibility(View.INVISIBLE);
                    editContrasenia2.setVisibility(View.INVISIBLE);
                    contenedorCalle.setVisibility(View.INVISIBLE);
                    contenedorNumeracion.setVisibility(View.INVISIBLE);

            }
        }
    }

    private int setDatosSpinner(Spinner spinner,String datoSpinner) {
        int pos = 0;
        for ( int i = 0; i < spinner.getCount(); i++) {

            if (spinner.getItemAtPosition(i).toString().equals(datoSpinner)) {
                spinner.setSelection(i);
                pos =spinner.getSelectedItemPosition();

            }
        }
        return pos;

    }


    public void changeSaveFlag(Boolean b, int i){
        saveFlag[i] = b;
    }

    public boolean recorrerFlags(){
        boolean result = true;
        for (int index = 0 ; index < saveFlag.length ; index++){
            if (Boolean.FALSE.equals(saveFlag[index])){
                result = false;
            }
        }

        return result;
    }


    public void onRadioButtonClicked(View view) {
        boolean marcado = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonNombre:
                if (marcado){
                    editNombre.setEnabled(true);
                    editNombre.setText("");
                    editNombre.setHint("Ingrese nombre");
                    editNombre.requestFocus();
                }
                break;
            case R.id.radioButtonApellido:
                if (marcado){
                    editApellido.setEnabled(true);
                    editApellido.setText("");
                    editApellido.setHint("Ingrese apellido");
                    editApellido.requestFocus();
                }
                break;
            case R.id.radioButtonDni:
                if (marcado){
                    editDni.setEnabled(true);
                    editDni.setText("");
                    editDni.setHint("Ingrese DNI");
                    editDni.requestFocus();
                }
                break;
            case R.id.radioButtonNacionalidad:
                if (marcado) {
                    spinnerNacionalidad.setEnabled(true);
                    spinnerNacionalidad.setSelection(0);
                }
                break;
            case R.id.radioButtonProvincia:
                if (marcado) {
                    spinnerProvinciaResidencia.setEnabled(true);
                    spinnerProvinciaResidencia.setSelection(0);
                    spinnerLocalidadResidencia.setSelection(0);
                }
                break;
            case R.id.radioButtonLocalidad:
                if (marcado) {
                    spinnerLocalidadResidencia.setEnabled(true);
                    spinnerLocalidadResidencia.setSelection(0);
                }
                break;

            case R.id.radioButtonDomicilio:
                if (marcado) {
                    contenedorCalle.setVisibility(View.VISIBLE);
                    contenedorNumeracion.setVisibility(View.VISIBLE);
                    editDireccionCalle.requestFocus();

                }
                break;
            case R.id.radioButtonCarrera:
                if (marcado) {
                    spinnerCarreraGrado.setEnabled(true);
                    spinnerCarreraGrado.setSelection(0);
                }
                break;
            case R.id.radioButtonCorreo:
                if (marcado){
                    editCorreo.setEnabled(true);
                    editCorreo.setText("");
                    editCorreo.setHint("Ingrese correo");
                    editCorreo.requestFocus();
                }
                break;
            case R.id.radioButtonUsuario:
                if (marcado){
                    editNombreUsuario.setEnabled(true);
                    editNombreUsuario.setText("");
                    editNombreUsuario.setHint("Ingrese usuario");
                    editNombreUsuario.requestFocus();
                }
                break;
            case R.id.radioButtonPassword:
                if (marcado){
                    editContrasenia.setVisibility(View.VISIBLE);
                    tvEditarPassword.setVisibility(View.VISIBLE);
                    editContrasenia2.setVisibility(View.VISIBLE);
                    editContrasenia.setHint("Ingrese contreseña");
                    editContrasenia2.setHint("Repita contreseña");
                }
                break;

        }
    }
}
