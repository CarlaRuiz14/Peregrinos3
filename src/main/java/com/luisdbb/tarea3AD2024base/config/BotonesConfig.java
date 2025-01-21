package com.luisdbb.tarea3AD2024base.config;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Clase para configurar iconos de botones en la interfaz gráfica.
 * 
 * <b>Funciones principales:</b>
 * <ul>
 * <li>Asignar imágenes a botones según su función.</li>
 * <li>Crear objetos <code>ImageView</code> reutilizables con un tamaño
 * uniforme.</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Component
public class BotonesConfig {

	@Autowired
	ResourceBundle resources;

	public void configImgFlecha(Button button) {
		String rutaFlecha = resources.getString("btnFlecha.icon");
		Image imgFlecha = new Image(getClass().getResourceAsStream(rutaFlecha));
		ImageView viewFlecha = new ImageView(imgFlecha);
		viewFlecha.setFitWidth(80);
		viewFlecha.setFitHeight(40);
		button.setGraphic(viewFlecha);
	}

	public void configImgSalir(Button button) {
		String rutaSalir = resources.getString("btnSalir.icon");
		Image imgSalir = new Image(getClass().getResourceAsStream(rutaSalir));
		ImageView viewSalir = new ImageView(imgSalir);
		viewSalir.setFitWidth(30);
		viewSalir.setFitHeight(30);
		button.setGraphic(viewSalir);
	}

	public void configImgLogout(Button button) {
		String rutaLog = resources.getString("btnLogout.icon");
		Image imgLogout = new Image(getClass().getResourceAsStream(rutaLog));
		ImageView viewLog = new ImageView(imgLogout);
		viewLog.setFitWidth(30);
		viewLog.setFitHeight(30);
		button.setGraphic(viewLog);
	}

	public void configImgVolver(Button button) {
		String rutaVolver = resources.getString("btnVolver.icon");
		Image imgVolver = new Image(getClass().getResourceAsStream(rutaVolver));
		ImageView viewVolver = new ImageView(imgVolver);
		viewVolver.setFitWidth(30);
		viewVolver.setFitHeight(30);
		button.setGraphic(viewVolver);
	}

	public void configImgLimpiar(Button button) {
		String rutaLimp = resources.getString("btnLimpiar.icon");
		Image imgLimp = new Image(getClass().getResourceAsStream(rutaLimp));
		ImageView viewLimp = new ImageView(imgLimp);
		viewLimp.setFitWidth(30);
		viewLimp.setFitHeight(30);
		button.setGraphic(viewLimp);
	}

	public ImageView createImageView(Image image) {
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(40);
		imageView.setFitHeight(40);
		imageView.setPreserveRatio(true);
		return imageView;
	}
}
