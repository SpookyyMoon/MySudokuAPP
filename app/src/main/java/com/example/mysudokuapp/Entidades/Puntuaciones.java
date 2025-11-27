package com.example.mysudokuapp.Entidades;

import java.io.Serializable;

public class Puntuaciones implements Serializable {
    String nombreUsuario;
    Number tiempoTardado;
    int pistasUsadas;
    String dificultad;

    public Puntuaciones(String nombreUsuario, Number tiempoTardado, int pistasUsadas, String dificultad) {
        this.nombreUsuario = nombreUsuario;
        this.tiempoTardado = tiempoTardado;
        this.pistasUsadas = pistasUsadas;
        this.dificultad = dificultad;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public int getPistasUsadas() {
        return pistasUsadas;
    }

    public void setPistasUsadas(int pistasUsadas) {
        this.pistasUsadas = pistasUsadas;
    }

    public Number getTiempoTardado() {
        return tiempoTardado;
    }

    public void setTiempoTardado(Number tiempoTardado) {
        this.tiempoTardado = tiempoTardado;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
