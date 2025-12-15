package com.example.mysudokuapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysudokuapp.Adaptadores.ApiAdapter;
import com.example.mysudokuapp.Adaptadores.PuntuacionesVistaAdaptador;
import com.example.mysudokuapp.Entidades.Puntuaciones;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PuntuacionesVista extends AppCompatActivity implements Callback<List<Puntuaciones>> {

    String dificultad;
    ImageView botonAtras;
    RecyclerView contenedorPuntuaciones;
    PuntuacionesVistaAdaptador adaptador;
    List<Puntuaciones> listaPuntuaciones = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.puntuaciones_vista);

        Intent intentPrevio = getIntent();
        dificultad = intentPrevio.getStringExtra("dificultadJuego");

        botonAtras = findViewById(R.id.botonAtras); // Asignación de la imagen a botonAtras
        contenedorPuntuaciones =  findViewById(R.id.contenedorPuntuaciones); // Asignacion del recycleview

        contenedorPuntuaciones.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new PuntuacionesVistaAdaptador(listaPuntuaciones);
        contenedorPuntuaciones.setAdapter(adaptador);


        Call<List<Puntuaciones>> call = ApiAdapter.getApiService().getPuntuaciones(dificultad);
        call.enqueue(this);

        botonAtras.setOnClickListener(v -> { // Click listener que detecta los clicks sobre la imagen
            Intent intent = new Intent(this, InicioAPP.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResponse(Call<List<Puntuaciones>> call, Response<List<Puntuaciones>> response) {
        if (response.isSuccessful() && response.body() != null) {
            listaPuntuaciones.clear();
            listaPuntuaciones.addAll(response.body());
            adaptador.notifyDataSetChanged();

            Log.d("onResponse puntuaciones", "Tamaño de la lista -> " + listaPuntuaciones.size());
        } else {
            Log.e("onResponse puntuaciones", "Error HTTP: " + response.code());
        }
    }


    @Override
    public void onFailure(Call<List<Puntuaciones>> call, Throwable t) {
        Log.e("onFailure puntuaciones", "Error al obtener puntuaciones", t);
    }

}
