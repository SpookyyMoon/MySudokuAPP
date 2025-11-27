package com.example.mysudokuapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class NuevaPartidaDificultad extends AppCompatActivity {

    ImageView botonAtras;
    String nombreJugador, dificultad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.nueva_partida_dificultad);
        Intent intentPrevio = getIntent();
        nombreJugador = intentPrevio.getStringExtra("nombreJugador");

        botonAtras = findViewById(R.id.botonAtras); // Asignación de la imagen a botonAtras

        botonAtras.setOnClickListener(v -> { // Click listener que detecta los clicks sobre la imagen
            Intent intent = new Intent(this, NuevaPartidaNombre.class);
            startActivity(intent);
        });
    }

    public void dificultadFacil(View view) {
        dificultad = "facil";
        Intent intent = new Intent(this, Partida.class);
        intent.putExtra("nombreJugador", nombreJugador);
        intent.putExtra("dificultadJuego", dificultad);
        startActivity(intent); // Inicia la actividad pasando la dificultad y el nombre de jugador
    }

    public void dificultadMedia(View view) {
        dificultad = "media";
        Intent intent = new Intent(this, Partida.class);
        intent.putExtra("nombreJugador", nombreJugador);
        intent.putExtra("dificultadJuego", dificultad);
        startActivity(intent); // Inicia la actividad pasando la dificultad y el nombre de jugador
    }

    public void dificultadDificil(View view) {
        dificultad = "dificil";
        Intent intent = new Intent(this, Partida.class);
        intent.putExtra("nombreJugador", nombreJugador);
        intent.putExtra("dificultadJuego", dificultad);
        startActivity(intent); // Inicia la actividad pasando la dificultad y el nombre de jugador
    }
}
