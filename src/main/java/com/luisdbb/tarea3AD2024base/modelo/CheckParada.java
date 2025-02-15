package com.luisdbb.tarea3AD2024base.modelo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Clase auxiliar para tabla de paradas en servicio (columna checkbox).
 * Envuelve un objeto Parada y añade un campo booleano
 * (usado para el checkbox en la tabla).
 */
public class CheckParada {

    private Parada parada;
    private BooleanProperty check;

    public CheckParada() {
        this(null, false);
    }
   
    public CheckParada(Parada parada, boolean checkInicial) {
        this.parada = parada;
        this.check = new SimpleBooleanProperty(checkInicial);
    }

    public Parada getParada() {
        return parada;
    }

    public void setParada(Parada parada) {
        this.parada = parada;
    }

    
    // metodos con booleanProperty para columna
    public BooleanProperty checkProperty() {
        return check;
    }

    public boolean isCheck() {
        return check.get();
    }

    public void setCheck(boolean value) {
        check.set(value);
    }
}
