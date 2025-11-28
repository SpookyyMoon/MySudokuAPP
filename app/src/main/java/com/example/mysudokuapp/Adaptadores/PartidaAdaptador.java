package com.example.mysudokuapp.Adaptadores;

public class PartidaAdaptador {

    public void recorrerTableroNumeros() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int columnaSeleccionada = i;
                int filaSeleccionada = j;
                generarCasillaNumero(columnaSeleccionada, filaSeleccionada);
            }
        }
    }
    public void generarCasillaNumero(int columnaSeleccionada, int filaSeleccionada) {
        int numeroGenerado = (int) (Math.random() * 9 + 1);
        if (esPosibleAgregarNumero(columnaSeleccionada, filaSeleccionada, numeroGenerado)) { // Llama a la función de comprobación

        }
        else{
            generarCasillaNumero(columnaSeleccionada, filaSeleccionada);
        }
    }

    public boolean esPosibleAgregarNumero(int columnaSeleccionada, int filaSeleccionada, int numeroGenerado) {
        boolean disponible = false;
        // Bucle nesteado que comprueba la disponibilidad de ese número dentro de ese sector
        for (int i = columnaSeleccionada; i < i +3; i++) {
            for (int j = filaSeleccionada; j < j+3; j++) {

            }
        }

        // Comprobación de la disponibilidad de ese número dentro de ese sector
        if (disponible) {
            for (int i = 0; i < 9; i++) {

            }
            for (int j = 0; j < 9; j++) {

            }
        }

        return disponible;
    }
}
