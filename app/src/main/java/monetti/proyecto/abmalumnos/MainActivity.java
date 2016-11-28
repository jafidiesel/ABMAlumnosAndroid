package monetti.proyecto.abmalumnos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override //Constructor por defecto
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.activity_main);

    }

    //Cambio de MainActivity a altaUsuario
    public void altaUsuario(View view) {
        Intent intent = new Intent(this, altaUsuario.class);
        startActivity(intent);
    }

    //Cambio de MainActivity a bajaUsuario
    public void bajaUsuario(View view) {
        Intent i = new Intent(this, bajaUsuario.class );
        startActivity(i);
    }


    public void imprimirMensaje(View view){ //Funcion para imprimir una alerta (mensaje sobre la pantalla)
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Alert message to be shown");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    //*********************************************************
    //generic dialog, takes in the method name and error message
    //*********************************************************
    private void messageBox(String method, String message)
    {
        Log.d("EXCEPTION: " + method,  message);

        AlertDialog.Builder messageBox = new AlertDialog.Builder(this);
        messageBox.setTitle(method);
        messageBox.setMessage(message);
        messageBox.setCancelable(false);
        messageBox.setNeutralButton("OK", null);
        messageBox.show();
    }

}
