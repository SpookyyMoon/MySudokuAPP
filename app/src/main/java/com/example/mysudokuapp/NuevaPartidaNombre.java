package com.example.mysudokuapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NuevaPartidaNombre extends AppCompatActivity {

    ImageView botonAtras;
    EditText inputNombre;
    String nombreJugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.nueva_partida_nombre);

        botonAtras = findViewById(R.id.botonAtras); // Asignación de la imagen a botonAtras
        inputNombre = findViewById(R.id.inputNombre); // Asignación del EditText a inputNombre

        botonAtras.setOnClickListener(v -> { // Click listener que detecta los clicks sobre la imagen
            Intent intent = new Intent(this, InicioAPP.class);
            startActivity(intent);
        });
    }

    public void siguiente(View view) {
        nombreJugador = inputNombre.getText().toString(); // Almacena el nombre del jugador, sacado de inputNombre en nombreJugador
        if (nombreJugador.isEmpty() || nombreJugador.isBlank()) {
            alertaSencilla(
                    "Nombre de jugador",
                    "¡Debes escoger un nombre de jugador!"
            );
        }
        else {
            Intent intent = new Intent(this, NuevaPartidaDificultad.class);
            intent.putExtra("nombreJugador", nombreJugador); // Añade al intent nombreJugador para pasarlo entre actividades
            startActivity(intent);
        }
    }

    public void alertaSencilla(String tituloAlerta, String mensajeAlerta) {
        AlertDialog alertDialog = new AlertDialog.Builder(NuevaPartidaNombre.this).create();
        alertDialog.setTitle(tituloAlerta);
        alertDialog.setIcon(R.drawable.logo_alert);
        alertDialog.setMessage(mensajeAlerta);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog.show();
    }
}
