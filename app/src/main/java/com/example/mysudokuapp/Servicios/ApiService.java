package com.example.mysudokuapp.Servicios;


import com.example.mysudokuapp.Entidades.Puntuaciones;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("puntuaciones")
    Call<List<Puntuaciones>> getPuntuaciones();
    @POST("puntuaciones")
    Call<Puntuaciones> createPuntuacion(@Body Puntuaciones puntuaciones);
}
