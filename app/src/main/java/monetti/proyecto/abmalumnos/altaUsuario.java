package monetti.proyecto.abmalumnos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import static java.sql.Types.VARCHAR;

public class altaUsuario extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    EditText editNombre, editApellido, editNombreUsuario, editDni, editLocalidad, editDireccion, editCorreo, editContrasenia, editContrasenia2;
    Spinner spinnerNacionalidad, spinnerPais, spinnerProvincia, spinnerCarrera;
    Button buttonGuardar;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta_usuario);
        editDni             = (EditText)findViewById(R.id.dni);
        editNombre          = (EditText)findViewById(R.id.nombre);
        editApellido        = (EditText)findViewById(R.id.apellido);
        editNombreUsuario   = (EditText)findViewById(R.id.nombreUsuario);
        editCorreo          = (EditText)findViewById(R.id.correo);
        editContrasenia     = (EditText)findViewById(R.id.contrasenia);
        editContrasenia2    = (EditText)findViewById(R.id.contrasenia2);
        spinnerNacionalidad = (Spinner) findViewById(R.id.spinnerNacionalidad);
        spinnerPais         = (Spinner) findViewById(R.id.spinnerPais);
        spinnerProvincia    = (Spinner) findViewById(R.id.spinnerProvincia);
        editLocalidad       = (EditText)findViewById(R.id.localidad);
        editDireccion       = (EditText)findViewById(R.id.direccion);
        spinnerCarrera      = (Spinner) findViewById(R.id.spinnerCarrera);
        buttonGuardar       = (Button) findViewById(R.id.botonGuardar);
        buttonGuardar.setOnClickListener(this);
        db=openOrCreateDatabase("DBAlumnos", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS alumno(dni VARCHAR, nombre VARCHAR, apellido VARCHAR,nombreUsuario VARCHAR,correo VARCHAR, contrasenia VARCHAR, nacionalidad VARCHAR, pais VARCHAR, provincia VARCHAR, localidad VARCHAR, direccion VARCHAR, carrera VARCHAR);");
        armarSpinners();

        /*
        editDni
        editNombre
        editApellido
        editNombreUsuario
        editCorreo
        editContrasenia
        editContrasenia2
        spinnerNacionalidad
        spinnerPais
        spinnerProvincia
        editLocalidad
        editDireccion
        spinnerCarrera
        */
    }

    private void armarSpinners(){
        //Armamos spinner nacionalidad
        ArrayAdapter<CharSequence> adapterNacionalidad = ArrayAdapter.createFromResource(this, R.array.arraySpinnerNacionalidad, android.R.layout.simple_spinner_item);
        adapterNacionalidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerNacionalidad.setAdapter(adapterNacionalidad);
        this.spinnerNacionalidad.setOnItemSelectedListener(this);

        //Armamos spinner pais
        ArrayAdapter<CharSequence> adapterPais = ArrayAdapter.createFromResource(this, R.array.arraySpinnerPais, android.R.layout.simple_spinner_item);
        adapterPais.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerPais.setAdapter(adapterPais);
        this.spinnerPais.setOnItemSelectedListener(this);

        //Armamos spinner provincia
        ArrayAdapter<CharSequence> adapterProvincia = ArrayAdapter.createFromResource(this, R.array.arraySpinnerProvincia, android.R.layout.simple_spinner_item);
        adapterProvincia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerProvincia.setAdapter(adapterProvincia);
        this.spinnerProvincia.setOnItemSelectedListener(this);

        //Armamos spinner carrera
        ArrayAdapter<CharSequence> adapterCarrera = ArrayAdapter.createFromResource(this, R.array.arraySpinnerCarrera, android.R.layout.simple_spinner_item);
        adapterCarrera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerCarrera.setAdapter(adapterCarrera);
        this.spinnerCarrera.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView <? > parent, View view, int pos, long id) {}
    public void onNothingSelected(AdapterView <? > parent) {}

    public void onClick(View view) {
        if(view  == buttonGuardar) {
            if (comprobarCamposVacios()) {
                showMessage("Error", "Todos los campos deben estar completos");
                //En este mensaje seria mejor mostrar cual o cuales campos no estan completados

            } else {
                db.execSQL(armarQueryInsert(editDni, editNombre, editApellido, editNombreUsuario, editCorreo, editContrasenia, spinnerNacionalidad, spinnerPais, spinnerProvincia, editLocalidad, editDireccion, spinnerCarrera));
                clearText();
                showMessage("Exito", "Registro agregado");
                mostrarInformacionGuardada(editDni);
            }
        }

    }

    public void mostrarInformacionGuardada(EditText editText1){
        Cursor c = db.rawQuery(armarQuerySelect(editText1));
    }

    public String armarQueryInsert(EditText editDni,EditText editNombre,EditText editApellido,EditText editNombreUsuario,EditText editCorreo, EditText editContrasenia, Spinner spinnerNacionalidad,Spinner spinnerPais, Spinner spinnerProvincia, EditText editLocalidad, EditText editDireccion, Spinner spinnerCarrera ){

        String queryInsert = "INSERT INTO alumno VALUES('" +','+ this.editDni.getText().toString() +','+ this.editNombre.getText().toString() +','+ this.editApellido.getText().toString() +','+ this.editNombreUsuario.getText().toString() +','+ this.editContrasenia.getText().toString() +','+ getSpinnerValue(this.spinnerNacionalidad) +','+ getSpinnerValue(this.spinnerPais) +','+ getSpinnerValue(this.spinnerProvincia) +','+ this.editLocalidad.getText().toString() +','+ this.editDireccion.getText().toString() +','+ getSpinnerValue(this.spinnerCarrera) +','+ ')';
        return queryInsert;
    }

    public String armarQuerySelect(EditText editTextDni){
        String resultado = " ";

        return resultado;
    }

    public void clearText(){
        editDni.setText("");
        editNombre.setText("");
        editApellido.setText("");
        editNombreUsuario.setText("");
        editCorreo.setText("");
        editContrasenia.setText("");
        editContrasenia2.setText("");
        spinnerNacionalidad.setSelection(0);
        spinnerPais.setSelection(0);
        spinnerProvincia.setSelection(0);
        editLocalidad.setText("");
        editDireccion.setText("");
        spinnerCarrera.setSelection(0);

    }

    public String getSpinnerValue(Spinner spinner1){
        String resultado = spinner1.getSelectedItem().toString();

        return resultado ;
    }

    public boolean comprobarCamposVacios(){
        boolean result =    editNombre.getText().toString().trim().length() == 0 ||
                            editApellido.getText().toString().trim().length() == 0 ||
                            editNombreUsuario.getText().toString().trim().length() == 0 ||
                            editDni.getText().toString().trim().length() == 0 ||
                            editLocalidad.getText().toString().trim().length() == 0 ||
                            editDireccion.getText().toString().trim().length() == 0 ||
                            editCorreo.getText().toString().trim().length() == 0 ||
                            editContrasenia.getText().toString().trim().length() == 0 ||
                            editContrasenia2.getText().toString().trim().length() == 0;
                            //Falta la comprobacion de los spinners por el estado "vacio"
        return result;
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void volver(View view){
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }


}
