package com.example.mysudokuapp;

import static android.view.View.VISIBLE;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.gridlayout.widget.GridLayout;

import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.mysudokuapp.Adaptadores.ApiAdapter;
import com.example.mysudokuapp.Adaptadores.PartidaAdaptador;
import com.example.mysudokuapp.Entidades.Puntuaciones;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Partida extends AppCompatActivity {

    String nombreJugador, dificultad;
    GridLayout tableroSudoku;
    TextView celdaSeleccionadaText, tiempoConteo, textoPistas, pistasConteo;
    Timer timer;
    TimerTask timerTask;
    Double tiempo = 0.0;
    int fila = -1;
    int columna = -1;
    int contadorPistas = 0;
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

        tiempoConteo = findViewById(R.id.tiempoConteo);
        textoPistas = findViewById(R.id.textoPistas);
        pistasConteo = findViewById(R.id.pistasConteo);

        if (dificultad.equals("facil")) {
            textoPistas.setVisibility(VISIBLE);
            pistasConteo.setVisibility(VISIBLE);
        }

        tableroSudoku = findViewById(R.id.tableroSudoku); // Asigna el GridLayout a tableroSudoku
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                celdaSeleccionada = (int[]) v.getTag();
                fila = celdaSeleccionada[0];
                columna = celdaSeleccionada[1];
                celdaSeleccionadaText = (TextView) v;
                Log.d("onClick", "Celda seleccionada -> " + "Fila: " + fila + " Columna: " + columna);
            }
        };
        generarTablero(listener);
        iniciarContadorTiempo();

        Log.d("getIntent", "Nombre introducido -> " + nombreJugador);
        Log.d("getIntent", "Dificultad seleccionada -> " + dificultad);
    }

    private void iniciarContadorTiempo() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tiempo++;
                        tiempoConteo.setText(getTiempoConteo());
                    }
                });
            }

            private String formatearTiempo(int segundos, int minutos) {
                return String.format("%02d", minutos) + " : " + String.format("%02d", segundos);
            }

            private String getTiempoConteo() {
                int tiempoRedondeado = (int) Math.round(tiempo);

                int segundos = ((tiempoRedondeado % 86500) % 3600) % 60;
                int minutos = ((tiempoRedondeado % 86500) % 3600) / 60;

                return formatearTiempo(segundos, minutos);
            }
        };
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
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

    public void comprobarVictoriaBoton(View v) {

        AlertDialog alertDialog = new AlertDialog.Builder(Partida.this).create();
        alertDialog.setTitle("Comprobar tablero");
        alertDialog.setIcon(R.drawable.logo_texto);
        alertDialog.setMessage("¿Quieres comprobar el tablero? La partida terminará.");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        if (!tableroCompleto()) {
                            alertaSencilla("Tablero incompleto", "Debes completar todas las casillas.");
                            return;
                        }

                        if (tablerosIguales()) {
                            alertaSencilla("¡Victoria!", "Has completado el Sudoku correctamente.");
                            timer.cancel();

                            Puntuaciones nuevaPuntuacion = new Puntuaciones(nombreJugador, tiempo, contadorPistas, dificultad);
                            Call<Puntuaciones> call = ApiAdapter.getApiService().createPuntuacion(nuevaPuntuacion);
                            call.enqueue(new Callback<Puntuaciones>() {
                                @Override
                                public void onResponse(Call<Puntuaciones> call, Response<Puntuaciones> response) {
                                    Log.d("onResponse partida", "Puntuación guardada -> " + nuevaPuntuacion.getNombreUsuario());
                                    Intent intent = new Intent(Partida.this, InicioAPP.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<Puntuaciones> call, Throwable t) {
                                    Log.e("onFailure partida", "Error al guardar puntuación", t);
                                }
                            });
                        } else {
                            alertaSencilla("Incorrecto", "Hay errores en el tablero.");
                            timer.cancel();
                        }
                    }

                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    private boolean tableroCompleto() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tableroJuego[i][j] == 0) { // Recorre el tablero comprobando que las celdas no estén vacias
                    return false;
                }
            }
        }
        return true;
    }

    private boolean tablerosIguales() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (tableroJuego[i][j] != tableroCompleto[i][j]) { // Recorre el tablero comparando cada casilla
                    return false;
                }
            }
        }
        return true;
    }

    public boolean comprobarNumeroPosible(int fila, int columna) {
        if (tableroCompleto[fila][columna] == tableroJuego[fila][columna]) {
            return true;
        } else {
            return false;
        }
    }

    public void recibirPista(View v) {
        if (celdaSeleccionadaText == null) {
            return;
        }

        if (celdaSeleccionadaText.getText().equals("") || celdaSeleccionadaText.getText() == null && dificultad.equals("facil")) {
            alertaMostrar("pista");
        } else if (celdaSeleccionadaText.getText().equals("") || celdaSeleccionadaText.getText() == null) {
            alertaMostrar("pistaCeldaOcupada");
        } else if (dificultad.equals("media") || dificultad.equals("dificil")) {
            alertaMostrar("pistaDificultad");
        }
    }

    public void alertaMostrar(String tipo) {

        switch (tipo) {

            case "comprobar":
                alertaDoble(
                        "Comprobar tablero",
                        "¿Estás seguro de comprobar el tablero? La partida terminará.",
                        () ->  comprobarVictoriaBoton(null)
                );
                break;

            case "pista":
                alertaDoble(
                        "Recibir pista",
                        "¿Quieres recibir una pista? Se tendrá en cuenta en la puntuación.",
                        () -> aplicarPista()
                );
                break;

            case "pistaDificultad":
                alertaSencilla(
                        "Recibir pista",
                        "Las pistas solo están disponibles en dificultad fácil.\n" +
                                "Estás jugando en dificultad: " + dificultad
                );
                break;

            case "pistaCeldaOcupada":
                alertaSencilla(
                        "Celda ocupada",
                        "No puedes recibir una pista en una celda que ya tiene un número."
                );
                break;

            case "tableroNoCompleto":
                alertaSencilla(
                        "Tablero incompleto",
                        "Debes completar todas las casillas antes de poder comprobar."
                );
                break;

            case "rendirse":
                alertaDoble(
                        "Rendirse",
                        "¿Seguro que quieres rendirte? Volverás al menú principal.",
                        () -> {
                            Intent intent = new Intent(this, InicioAPP.class);
                            startActivity(intent);
                        }
                );
                break;
        }
    }


    public void alertaSencilla(String tituloAlerta, String mensajeAlerta) {
        AlertDialog alertDialog = new AlertDialog.Builder(Partida.this).create();
        alertDialog.setTitle(tituloAlerta);
        alertDialog.setIcon(R.drawable.logo_texto);
        alertDialog.setMessage(mensajeAlerta);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void alertaDoble(String titulo, String mensaje, Runnable accionAceptar) {
        AlertDialog alertDialog = new AlertDialog.Builder(Partida.this).create();
        alertDialog.setTitle(titulo);
        alertDialog.setIcon(R.drawable.logo_texto);
        alertDialog.setMessage(mensaje);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        accionAceptar.run();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void aplicarPista() {
        if (tableroCompleto == null || celdaSeleccionadaText == null) {
            return;
        }
        int valor = tableroCompleto[fila][columna];
        celdaSeleccionadaText.setText(String.valueOf(valor));
        celdaSeleccionadaText.setTextColor(Color.rgb(103, 200, 144));
        tableroJuego[fila][columna] = valor;
        Log.d("recibirPista", "Pista: " + valor + " en (" + fila + "," + columna + ")");
        contadorPistas++;
        pistasConteo.setText(String.valueOf(contadorPistas));
    }

    public void pulsarNumero(View v) {
        if (celdaSeleccionadaText == null) {
            return;
        }

        int valor = 0;
        int id = v.getId();

        if (id == R.id.numeroUno) {
            valor = 1;
        } else if (id == R.id.numeroDos) {
            valor = 2;
        } else if (id == R.id.numeroTres) {
            valor = 3;
        } else if (id == R.id.numeroCuatro) {
            valor = 4;
        } else if (id == R.id.numeroCinco) {
            valor = 5;
        } else if (id == R.id.numeroSeis) {
            valor = 6;
        } else if (id == R.id.numeroSiete) {
            valor = 7;
        } else if (id == R.id.numeroOcho) {
            valor = 8;
        } else if (id == R.id.numeroNueve) {
            valor = 9;
        }

        celdaSeleccionadaText.setText(String.valueOf(valor));
        tableroJuego[fila][columna] = valor;
        Log.d("pulsarNumero", "Número introducido -> " + valor + " en la celda -> Fila: " + fila + " Columna: " + columna);
        if (Objects.equals(dificultad, "facil") || Objects.equals(dificultad, "media")) { // Solo se aplica en caso de que la dificultad sea facil o media
            if (!comprobarNumeroPosible(fila, columna)) {
                celdaSeleccionadaText.setTextColor(Color.RED); // En caso de que la solución no sea correcta se marca en rojo
            } else {
                celdaSeleccionadaText.setTextColor(Color.rgb(126, 64, 243)); // En caso de haber cambiado la celda, se restaura el color original
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
        alertaMostrar("rendirse");
    }
}