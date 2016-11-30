package com.example.root.pruebas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void acercade(View view) {

        startActivity(new Intent(this, SecondActivity.class ));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
}
