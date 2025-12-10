package com.example.mysudokuapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import androidx.gridlayout.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.mysudokuapp.Adaptadores.PartidaAdaptador;

import java.util.Objects;

public class Partida extends AppCompatActivity {

    String nombreJugador, dificultad;
    GridLayout tableroSudoku;
    TextView celdaSeleccionadaText;
    int fila = -1;
    int columna = -1;
    int[] celdaSeleccionada;
    int[][] tableroJuego;
    int[][] tableroCompleto;



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
                celdaSeleccionada = (int[]) v.getTag();
                fila = celdaSeleccionada[0];
                columna = celdaSeleccionada[1];
                celdaSeleccionadaText = (TextView) v;
                Log.d("onClick", "Celda seleccionada -> " + "Fila: " + fila + " Columna: " + columna);
            }
        };
        generarTablero(listener);

        Log.d("getIntent", "Nombre introducido -> " + nombreJugador);
        Log.d("getIntent", "Dificultad seleccionada -> " + dificultad);
    }

    private void generarTablero(View.OnClickListener listener) {
        int[][] tableroNumeros = llenarTableroSudoku();

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

                    if (tableroNumeros[i][j] != 0) {
                        celda.setText(String.valueOf(tableroNumeros[i][j]));
                    }

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
        Log.d("generarTablero", "Tablero generado correctamente!");
    }

    public boolean comprobarNumeroPosible(int fila, int columna) {
        if (tableroCompleto[fila][columna] == tableroJuego[fila][columna]) {
            return true;
        }
        else {
            return false;
        }
    }

    public void recibirPista(View v) {
        if (celdaSeleccionadaText == null){
            return;
        }

        if (celdaSeleccionadaText.getText() == "" || celdaSeleccionadaText.getText() == null && dificultad.equals("facil")) {
            int valor = tableroCompleto[fila][columna];
            celdaSeleccionadaText.setText(String.valueOf(valor));
            tableroJuego[fila][columna] = valor;
            celdaSeleccionadaText.setTextColor(Color.GREEN); // Si la celda está vacia y la dificultad es facil, se rellena la casilla y en color verde
            Log.d("recibirPista", "Pista recibida (Número: + " + valor + " en la celda -> Fila: " + fila + " Columna: " + columna);
        }
    }

    public void pulsarNumero(View v) {
        if (celdaSeleccionadaText == null){
            return;
        }

        if (celdaSeleccionadaText.getText() == "" || celdaSeleccionadaText.getText() == null || dificultad.equals("facil") || dificultad.equals("media")) {
            int valor = 0;
            int id = v.getId();

            if (id == R.id.numeroUno){
                valor = 1;
            }
            else if (id == R.id.numeroDos){
                valor = 2;
            }
            else if (id == R.id.numeroTres){
                valor = 3;
            }
            else if (id == R.id.numeroCuatro){
                valor = 4;
            }
            else if (id == R.id.numeroCinco){
                valor = 5;
            }
            else if (id == R.id.numeroSeis){
                valor = 6;
            }
            else if (id == R.id.numeroSiete){
                valor = 7;
            }
            else if (id == R.id.numeroOcho){
                valor = 8;
            }
            else if (id == R.id.numeroNueve){
                valor = 9;
            }

            celdaSeleccionadaText.setText(String.valueOf(valor));
            tableroJuego[fila][columna] = valor;
            Log.d("pulsarNumero", "Número introducido -> " + valor + " en la celda -> Fila: " + fila + " Columna: " + columna);
            if (Objects.equals(dificultad, "facil") || Objects.equals(dificultad, "media")) { // Solo se aplica en caso de que la dificultad sea facil o media
                if (!comprobarNumeroPosible(fila, columna)) {
                    celdaSeleccionadaText.setTextColor(Color.RED); // En caso de que la solución no sea correcta se marca en rojo
                }
                else{
                    celdaSeleccionadaText.setTextColor(Color.BLACK); // En caso de haber cambiado la celda, se restaura el color original
                }
            }
        }
    }

    private int[][] llenarTableroSudoku() {
        PartidaAdaptador adaptador = new PartidaAdaptador();

        adaptador.recorrerTableroNumeros();
        tableroCompleto = adaptador.getTablero(); // Guardado del tablero completo
        tableroJuego = new int[9][9]; // Tablero vacio creado para el juego

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tableroJuego[i][j] = tableroCompleto[i][j]; // Copiado de tablero con soluciones al tablero de juego vacío mediante un bucle
            }
        }

        boolean[][] tableroVaciado = new boolean[9][9];
        int vaciadas = 0;
        while (vaciadas < 45) { // Se vacian 45 celdas aleatorias
            int i = (int) (Math.random() * 9);
            int j = (int) (Math.random() * 9);

            if (!tableroVaciado[i][j]) {  // Se comprueba que no se haya vaciado ya esa celda para asegurar que una no se vacia varias veces
                tableroJuego[i][j] = 0;
                tableroVaciado[i][j] = true;
                vaciadas++;
            }
        }
        Log.d("llenarTableroSudoku", "Tablero de juego listo!");
        return tableroJuego;
    }

    public void rendirse(View view) {
        Intent intent = new Intent(this, InicioAPP.class);
        startActivity(intent);
    }
}
