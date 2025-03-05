package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.stereotype.Component;

import javafx.scene.control.Label;

/**
 * Componente auxiliar para mostrar mensajes de retroalimentación en un Label.
 * <ul>
 * <li>Proporciona métodos para indicar mensajes de validación "válidos" o
 * "inválidos".</li>
 * <li>Actualiza las clases de estilo del Label para reflejar el estado de la
 * validación.</li>
 * </ul>
 * 
 * @author
 * @since 28/12/2024
 */
@Component
public class LabelFeed {

	public void mostrarTxtInvalido(Label lblFeed, String mensaje) {
		lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
		lblFeed.getStyleClass().add("lblFeedInvalido");
		lblFeed.setText(mensaje);
	}

	public void mostrarTxtValido(Label lblFeed, String mensaje) {

		lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
		lblFeed.getStyleClass().add("lblFeedValido");
		lblFeed.setText(mensaje);
	}
}
