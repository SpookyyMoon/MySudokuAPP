package com.example.mysudokuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Partida extends AppCompatActivity {

    String nombreJugador, dificultad;
    GridLayout tableroSudoku;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.partida);
        Intent intentPrevio = getIntent();
        nombreJugador = intentPrevio.getStringExtra("nombreJugador");
        dificultad = intentPrevio.getStringExtra("dificultadJuego");

        tableroSudoku = findViewById(R.id.tableroSudoku); // Asigna el GridLayout a tableroSudoku
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int[] celdaSeleccionada = (int[]) v.getTag();
                int fila = celdaSeleccionada[0];
                int columna = celdaSeleccionada[1];
                Log.d("onClick", "Celda seleccionada -> " + "Fila: " + fila + " Columna: " + columna);
            }
        };
        generarTablero(listener);

        Log.d("getIntent", "Nombre introducido -> " + nombreJugador);
        Log.d("getIntent", "Dificultad seleccionada -> " + dificultad);
    }

    private void generarTablero(View.OnClickListener listener) {
        tableroSudoku.post(() -> { // Evita que las mediciones de alto y ancho se tomen antes de estar cargando la vista (Devolverian 0) fuerza a ejecutar el estado post (medicion) de Android Studio
            int anchoGrid = tableroSudoku.getWidth();
            int altoGrid = tableroSudoku.getHeight();

            int tamanoCeldas = Math.min(anchoGrid, altoGrid) / 10; // Calculo del tamaño de cada celda para que entren 9x9 dentro del GridLayout

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {

                    TextView celda = new TextView(this); //Gener textviews por cada celda a traves del bucle horizontal y diagonal
                    celda.setWidth(tamanoCeldas);
                    celda.setHeight(tamanoCeldas);
                    celda.setGravity(Gravity.CENTER);
                    celda.setTextSize(16f);
                    celda.setBackground(ContextCompat.getDrawable(this, R.drawable.fondo_celdas_sudoku)); // Aplica el draweable (Diseño visual del tablero) a las celdas

                    celda.setTag(new int[]{i, j}); // Tagea la celda con su correspondiente columna y fila (EJ: 0,0)

                    celda.setOnClickListener(listener);

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams(); // Estancia del objeto params de GridLayout

                    int izquierda = 5, arriba = 3, derecha = 4, abajo = 2; // Márgenes preestablecidos (Izquierda > derecha para centrar contenido, Arriba > abajo para centrar contenido)

                    if (j % 3 == 0) {
                        izquierda = 10; // Comprueba si la columna que deberia empezar el bloque realmente lo empieza, si su resto es 0 (3%3 = 1 -> Su resto es 0)
                    }
                    if (i % 3 == 0) {
                        arriba = 12; // Comprueba si la fila que deberia empezar el bloque realmente lo empieza, si su resto es 0 (3%3 = 1 -> Su resto es 0)
                    }

                    params.setMargins(izquierda, arriba, derecha, abajo);

                    tableroSudoku.addView(celda, params);
                }
            }
        });
    }

    public void rendirse(View view) {
        Intent intent = new Intent(this, InicioAPP.class);
        startActivity(intent);
    }
}
