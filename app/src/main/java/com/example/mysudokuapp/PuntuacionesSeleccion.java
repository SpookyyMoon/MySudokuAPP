package com.example.mysudokuapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PuntuacionesSeleccion extends AppCompatActivity {

    String dificultad;
    ImageView botonAtras;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.puntuaciones_seleccion);

        botonAtras = findViewById(R.id.botonAtras); // Asignación de la imagen a botonAtras

        botonAtras.setOnClickListener(v -> { // Click listener que detecta los clicks sobre la imagen
            Intent intent = new Intent(this, InicioAPP.class);
            startActivity(intent);
        });
    }

    public void dificultadFacil(View view) {
        dificultad = "facil";
    }

    public void dificultadMedia(View view) {
        dificultad = "media";
    }

    public void dificultadDificil(View view) {
        dificultad = "dificil";
    }
}
