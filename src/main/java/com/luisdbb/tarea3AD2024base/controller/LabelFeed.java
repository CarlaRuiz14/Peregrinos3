package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.stereotype.Component;

import javafx.scene.control.Label;

@Component
public class LabelFeed {

	public void mostrarTxtInvalido(Label lblFeed,String mensaje) {
		lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
		lblFeed.getStyleClass().add("lblFeedInvalido");
		lblFeed.setText(mensaje);		
	}

	public void mostrarTxtValido(Label lblFeed,String mensaje) {

		lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
		lblFeed.getStyleClass().add("lblFeedValido");
		lblFeed.setText(mensaje);		
	}
}
