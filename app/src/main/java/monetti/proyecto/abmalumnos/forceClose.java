package monetti.proyecto.abmalumnos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class forceClose extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.crash_activity);



        // Your mechanism is ready now.. In this activity from anywhere
        // if you get force close error it will be redirected to the CrashActivity.
    }
    private void volver(View view){
        Intent i = new Intent(this, SecondActivity.class );
        startActivity(i);
    }
}