package monetti.proyecto.abmalumnos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class bajaUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baja_usuario);
    }

    public void volver(View view){
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }
}
