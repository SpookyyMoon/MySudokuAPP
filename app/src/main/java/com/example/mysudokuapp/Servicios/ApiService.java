package com.example.mysudokuapp.Servicios;


import com.example.mysudokuapp.Entidades.Puntuaciones;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("puntuaciones/{dificultad}")
    Call<List<Puntuaciones>> getPuntuaciones(@Path("dificultad") String dificultad);
    @POST("puntuaciones")
    Call<Puntuaciones> createPuntuacion(@Body Puntuaciones puntuaciones);
}
