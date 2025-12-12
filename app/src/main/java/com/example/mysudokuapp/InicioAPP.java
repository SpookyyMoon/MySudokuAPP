package com.example.mysudokuapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InicioAPP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.inicio_app);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void nuevaPartida(View view) {
        Intent intent = new Intent(this, NuevaPartidaNombre.class);
        startActivity(intent);
    }

    public void puntuaciones(View view) {
        Intent intent = new Intent(this, PuntuacionesSeleccion.class);
        startActivity(intent);
    }

    public void salir(View view) {
        alertaSencilla(
                "Salir de la aplicación",
                "¿Estás seguro de que quieres abandonar la aplicación?"
        );
    }

    public void alertaSencilla(String tituloAlerta, String mensajeAlerta) {
        AlertDialog alertDialog = new AlertDialog.Builder(InicioAPP.this).create();
        alertDialog.setTitle(tituloAlerta);
        alertDialog.setIcon(R.drawable.logo_texto);
        alertDialog.setMessage(mensajeAlerta);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}