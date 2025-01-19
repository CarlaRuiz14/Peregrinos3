package com.luisdbb.tarea3AD2024base.config;

import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

@Component
public class Alertas {

	/**
	 * Método que contiene alerta de información
	 * 
	 * @param titulo
	 * @param mensaje
	 */
	public void alertaInformacion(String titulo, String mensaje) {
		Alert alerta = new Alert(Alert.AlertType.INFORMATION);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);		
		
		alerta.setContentText(mensaje);

		ResourceBundle bundle = ResourceBundle.getBundle("Bundle");
		String iconoRuta = bundle.getString("info.icon");
		if (iconoRuta != null && !iconoRuta.isEmpty()) {
			ImageView icono = new ImageView(new Image(iconoRuta));
			icono.setFitWidth(48);
			icono.setFitHeight(48);
			alerta.getDialogPane().setGraphic(icono);
		}

		String iconoVentana = bundle.getString("alertInfo.icon");
		if (iconoVentana != null && !iconoVentana.isEmpty()) {
			Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(iconoVentana));
		}
		alerta.getDialogPane().getStylesheets().add(Alertas.class.getResource("/styles/Styles.css").toExternalForm());

		alerta.showAndWait();
	}
	
	/**
	 * Método que contiene alerta de error
	 * 
	 * @param titulo
	 * @param mensaje
	 */
	public void alertaError(String titulo, String mensaje) {
		Alert alerta = new Alert(Alert.AlertType.ERROR);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(mensaje);

		ResourceBundle bundle = ResourceBundle.getBundle("Bundle");
		String iconoRuta = bundle.getString("error.icon");
		if (iconoRuta != null && !iconoRuta.isEmpty()) {
			ImageView icono = new ImageView(new Image(iconoRuta));
			icono.setFitWidth(48);
			icono.setFitHeight(48);
			alerta.getDialogPane().setGraphic(icono);
		}

		String iconoVentana = bundle.getString("error.icon");
		if (iconoVentana != null && !iconoVentana.isEmpty()) {
			Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(iconoVentana));
		}
		alerta.getDialogPane().getStylesheets().add(Alertas.class.getResource("/styles/Styles.css").toExternalForm());

		alerta.showAndWait();
	}

	/**
	 * Método que contiene alerta de confirmación con botones de si o no.
	 * 
	 * @param titulo
	 * @param mensaje
	 * @return true si hay respuesta y es true (Si)
	 */
	public boolean alertaConfirmacion(String titulo, String mensaje) {

		Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(mensaje);

		ResourceBundle bundle = ResourceBundle.getBundle("Bundle");
		String iconoRuta = bundle.getString("question.icon");
		if (iconoRuta != null && !iconoRuta.isEmpty()) {
			ImageView icono = new ImageView(new Image(iconoRuta));
			icono.setFitWidth(48);
			icono.setFitHeight(48);
			alerta.getDialogPane().setGraphic(icono);
		}
		
		String iconoVentana = bundle.getString("alertConf.icon");
		if (iconoVentana != null && !iconoVentana.isEmpty()) {
			Stage stage = (Stage) alerta.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image(iconoVentana));
		}

		ButtonType botonSi = new ButtonType("Sí");
		ButtonType botonNo = new ButtonType("No");
		alerta.getButtonTypes().setAll(botonSi, botonNo);

		alerta.getDialogPane().getStylesheets().add(Alertas.class.getResource("/styles/Styles.css").toExternalForm());

		
		Optional<ButtonType> respuesta = alerta.showAndWait();
		return respuesta.isPresent() && respuesta.get() == botonSi;
	}

}
