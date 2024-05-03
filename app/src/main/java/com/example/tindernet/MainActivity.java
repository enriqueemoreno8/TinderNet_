package com.example.tindernet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void abrirPantalla(View view) {
        // Crear un Intent para abrir la actividad de la página de login
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent); // Iniciar la actividad
    }



}