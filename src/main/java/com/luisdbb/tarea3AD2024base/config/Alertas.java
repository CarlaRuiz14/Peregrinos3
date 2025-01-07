package com.luisdbb.tarea3AD2024base.config;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alertas {
	
	/**
	 * Método que contiene alerta de información
	 * @param titulo
	 * @param mensaje
	 */
	public static void alertaInformacion(String titulo, String mensaje) {
	    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
	    alerta.setTitle(titulo);
	    alerta.setHeaderText(null);
	    alerta.setContentText(mensaje);
	    alerta.showAndWait();
	}
	
	/**
	 * Método que contiene alerta de confirmación con botones de si o no.
	 * @param titulo
	 * @param mensaje
	 * @return true si hay respuesta y es true (Si)
	 */
	public static boolean alertaConfirmacion(String titulo, String mensaje) {
       
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        
        ButtonType botonSi = new ButtonType("Sí");
        ButtonType botonNo = new ButtonType("No");
        alerta.getButtonTypes().setAll(botonSi, botonNo);
       
        Optional<ButtonType> respuesta = alerta.showAndWait();
        return respuesta.isPresent() && respuesta.get() == botonSi;
    }


}
