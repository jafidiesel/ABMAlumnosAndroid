package monetti.proyecto.abmalumnos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


public class crashReport extends AppCompatActivity {

    @Override //Constructor por defecto
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.crash_report);

        Intent intent= getIntent();
        Bundle b = intent.getExtras();

        if(b!=null){
            String j =(String) b.get("error");
            messageBox("Resumen",j);
        }

    }

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

    public void volver(View view){
        Intent i = new Intent(this, SecondActivity.class );
        startActivity(i);
    }
}
