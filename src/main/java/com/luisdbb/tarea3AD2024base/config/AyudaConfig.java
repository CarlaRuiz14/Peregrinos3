package com.luisdbb.tarea3AD2024base.config;

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

@Component
public class AyudaConfig {
	
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
	
	
	
	public void configInfo(String ruta) {
		WebView webView = new WebView();

		String url = getClass().getResource(ruta).toExternalForm();
		webView.getEngine().load(url);

		Stage helpStage = new Stage();
		helpStage.setTitle("Info");

		Scene helpScene = new Scene(webView, 600, 600);
		helpStage.setScene(helpScene);

		// Bloquear la ventana principal mientras se muestra la ayuda
		helpStage.initModality(Modality.APPLICATION_MODAL);
		helpStage.setResizable(false);

		helpStage.show();
	}
	
}
