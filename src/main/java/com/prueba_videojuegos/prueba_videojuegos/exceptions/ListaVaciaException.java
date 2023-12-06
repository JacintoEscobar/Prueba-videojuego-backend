package com.prueba_videojuegos.prueba_videojuegos.exceptions;

public class ListaVaciaException extends Exception {
    public ListaVaciaException() {
        super();
    }

    public ListaVaciaException(String mensaje) {
        super(mensaje);
    }
}
