package com.example.mysudokuapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PuntuacionesVista extends AppCompatActivity {

    String dificultad;
    ImageView botonAtras;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.puntuaciones_vista);

        botonAtras.setOnClickListener(v -> { // Click listener que detecta los clicks sobre la imagen
            Intent intent = new Intent(this, InicioAPP.class);
            startActivity(intent);
        });
    }

}
