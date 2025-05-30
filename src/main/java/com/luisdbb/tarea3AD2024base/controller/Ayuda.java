package com.luisdbb.tarea3AD2024base.controller;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Configuración de ayuda e información en la aplicación.
 * 
 * Funciones principales:
 * <ul>
 * <li>Configurar imágenes para componentes de ayuda.</li>
 * <li>Abrir ventanas modales con información en formato web.</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Component
public class Ayuda {

	@Autowired
	ResourceBundle resources;

	public void configImgInfo(Hyperlink hp) {
		String rutaInfo = resources.getString("info.icon");
		Image imagen = new Image(getClass().getResourceAsStream(rutaInfo));
		ImageView imageView = new ImageView(imagen);
		imageView.setFitWidth(30);
		imageView.setFitHeight(30);
		imageView.setPreserveRatio(true);
		hp.setGraphic(imageView);
	}

	public void configInfo(String ruta, Stage ventanaPadre) {
		WebView webView = new WebView();

		String url = getClass().getResource(ruta).toExternalForm();
		webView.getEngine().load(url);

		Stage helpStage = new Stage();
		helpStage.setTitle("Ayuda Estela");

		String iconPath = ResourceBundle.getBundle("Bundle").getString("info.icon");
		Image icon = new Image(getClass().getResource(iconPath).toExternalForm());
		helpStage.getIcons().add(icon);

		Scene helpScene = new Scene(webView, 540, 540);
		helpStage.setScene(helpScene);

		helpStage.initModality(Modality.APPLICATION_MODAL);
		helpStage.initOwner(ventanaPadre);
		helpStage.setResizable(false);

		ventanaPadre.setOpacity(0.7);
		helpStage.setOnCloseRequest(event -> ventanaPadre.setOpacity(1.0));

		helpStage.show();
	}
}
