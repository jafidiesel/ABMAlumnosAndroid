package monetti.proyecto.abmalumnos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    EditText nombreUsuario, passwordUsuario;
    Button buttonIngresar;

    Boolean[] saveFlag = new Boolean[2];
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreUsuario = (EditText) findViewById(R.id.nombreUsuarioBD);
        setOnFocusChangeListener(nombreUsuario);
        passwordUsuario = (EditText) findViewById(R.id.passwordBD);
        setOnFocusChangeListener(passwordUsuario);
        buttonIngresar = (Button) findViewById(R.id.buttonIngresar);

       // db=openOrCreateDatabase("DBAlumnos", Context.MODE_PRIVATE, null);
        //db.execSQL("CREATE TABLE IF NOT EXISTS alumno(dni VARCHAR, nombre VARCHAR, apellido VARCHAR,nombreUsuario VARCHAR,correo VARCHAR, password VARCHAR, paisOrigen VARCHAR, provincia VARCHAR, localidad VARCHAR, direccionCalle VARCHAR, numeracion VARCHAR, carrera VARCHAR, tipoUsuario VARCHAR);");


    }

    /*
    * function onClick
    * @param View view - Recibe una view
    * verifica que boton ha sido presionado:
        * Si se presiono  buttonIngresar
             * Se verifica que el usuario o contraseña sea distinto de vacio
             * Si los campos no estan vacios verifica  el formato de los datos (ver setOnFocusChangeListener)
             * Si no son vacios y tienen el formato correcto verifica la existencia del usuario y contraseña en la base de datos.
             * Consideracion: los usuarios tienen niveles de permisos que habilitan o deshabilitan determinadas funciones.
    *
    * */

    public void onClick(View view){

        DataBase dataBase = new DataBase(this,"DBAlumnos" +"",null, 1);
        db = dataBase.getReadableDatabase();
        ArrayList datosUsuario = new ArrayList();

        if (view == buttonIngresar){

            if ((nombreUsuario.getText().toString().trim().length() == 0) || (passwordUsuario.getText().toString().trim().length()== 0)){
               Toast.makeText(this,"Los campos no pueden estar vacios",Toast.LENGTH_SHORT).show();
            } else if(!recorrerFlags()) {
                Toast.makeText(this,"Los campos han sido ingresados incorrectamente",Toast.LENGTH_SHORT).show();

            } else  {

                Cursor c = db.rawQuery("SELECT * FROM Usuario WHERE nombreUsuario ='" + nombreUsuario.getText().toString().toUpperCase()+"'" + "AND password ='" + passwordUsuario.getText().toString()+"'" + "AND codTipoUsuario ='" + "usrAdm"+"'",null);
                Cursor q = db.rawQuery("SELECT tipoUsuario FROM alumno WHERE nombreUsuario ='" + nombreUsuario.getText().toString().toUpperCase()+"'" + "AND password ='" + passwordUsuario.getText().toString()+"'" + "AND tipoUsuario ='" + "usrAlm"+"'",null);

                if(c.getCount() == 0 & q.getCount() == 0){
                        Toast.makeText(this, "El usuario ingresado no existe",Toast.LENGTH_SHORT).show();
                       db.close();

                    } else if (!(c.getCount() == 0)){
                        if (c.moveToFirst()){

                            if (c.getString(2).equals("usrAdm") ){
                                datosUsuario.add(0,"usrAdm");
                                datosUsuario.add(1,nombreUsuario.getText());
                                ComunicadorClases.setObject(datosUsuario);
                                // ComunicadorClases es una clase que guarda el tipo de usuario, el usuario y
                                // la opcion modificar o alta
                                Intent i = new Intent(this, SecondActivity.class);
                                startActivity(i);
                                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                            }
                        }


                    } else if (!(q.getCount()==0)) {
                    if (q.moveToFirst()) {
                        if (q.getString(0).equals("usrAlm")) {
                            datosUsuario.add(0, "usrAlm");
                            datosUsuario.add(1, nombreUsuario.getText());
                            ComunicadorClases.setObject(datosUsuario);
                            // ComunicadorClases es una clase que guarda el tipo de usuario, el usuario y
                            // la opcion modificar o alta
                            Intent i = new Intent(this, SecondActivity.class);
                            startActivity(i);
                            overridePendingTransition(R.anim.left_in, R.anim.left_out);
                        }
                    }
                }
            }
        }
    }


    /*
    * function setOnFocusChangeListener
    *  @param EditText editText
    *  verifica el formato de lo que se ingreso en los campos por medio del !hasFocus,
    *  mostrando un mensaje de error y seteando en falso la bandera saveFlag que impide
    *  que se procese el ingreso a la app.
    *
    * */


    private void setOnFocusChangeListener(final EditText editText){

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {

                if(!hasFocus) {
                    switch(v.getId()){

                        case R.id.nombreUsuarioBD:

                            for (int i = 0 ; i < editText.length(); ++i ){
                                if (!Character.isLetterOrDigit(editText.getText().toString().charAt(i))){
                                    editText.setError("Solo se pueden ingresar caracteres alfanumericos");
                                    changeSaveFlag(false,0);
                                } else{
                                    editText.setError(null);
                                    changeSaveFlag(true,0);
                                }

                            }
                        break;


                        case R.id.passwordBD:
                            if (editText.getText().length()<8 ){
                                editText.setError("La contraseña debe contener 8 o más caracteres");
                                changeSaveFlag(false,1);
                            }else{
                                editText.setError(null);
                               changeSaveFlag(true,1);
                            }

                            break;
                    }
                }
            }
        });
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
}


