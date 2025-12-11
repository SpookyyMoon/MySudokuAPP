package com.example.mysudokuapp.Adaptadores;

public class PartidaAdaptador {

    private int[][] tablero = new int[9][9]; // Matriz de tablero

    public boolean recorrerTableroNumeros() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int filaSeleccionada = i;
                int columnaSeleccionada = j;
                if (tablero[filaSeleccionada][columnaSeleccionada] == 0) { //Comprieba que la celda este vacía
                    for (int numeroProbar = 1; numeroProbar <= 9; numeroProbar++) { // Recorrer números del 1-9 para probarlos todos
                        if (esPosibleAgregarNumero(filaSeleccionada, columnaSeleccionada, numeroProbar)) { // Comprueba que sea posible colocar ese número ahí
                            tablero[filaSeleccionada][columnaSeleccionada] = numeroProbar;
                            if (recorrerTableroNumeros()) { // Llamada recursiva a la siguiente celda
                                return true; // Si no ha habido errores se continua
                            }
                            tablero[filaSeleccionada][columnaSeleccionada] = 0; // Backtracking en caso de fallo
                        }
                    }
                    return false; // Si no se puede poner ningún numero en esa celda se retrocede a la anterior
                }
            }
        }
        return true; // Se detine el metodo cuando se han completado todas las celdas sin problemas
    }

    public boolean esPosibleAgregarNumero(int filaSeleccionada, int columnaSeleccionada, int numeroProbar) {
        // Comprobación de filas
        for (int i = 0; i < 9; i++) {
            if (tablero[filaSeleccionada][i] == numeroProbar){
                return false;
            }
        }

        // Comprobación de columnas
        for (int i = 0; i < 9; i++) {
            if (tablero[i][columnaSeleccionada] == numeroProbar){
                return false;
            }
        }

        // Comprobar bloques
        int filaInicio = (filaSeleccionada / 3) * 3;
        int columnaInicio = (columnaSeleccionada / 3) * 3;
        for (int i = filaInicio; i < filaInicio + 3; i++) {
            for (int j = columnaInicio; j < columnaInicio + 3; j++) {
                if (tablero[i][j] == numeroProbar){
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getTablero()
    {
        return tablero;
    }
}
