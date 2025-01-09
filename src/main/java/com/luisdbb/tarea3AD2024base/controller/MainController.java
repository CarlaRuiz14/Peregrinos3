package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */

@Controller
public class MainController implements Initializable {

	// Falta botón admin y alertas

	@FXML
	private Label lblTitulo;

	@FXML
	private Button btnFlecha;

	@FXML
	private Button btnAdmin;

	@FXML
	private Button btnSalir;

	// inyecciones
	@Lazy
	@Autowired
	private StageManager stageManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// config img btn flecha
		String rutaFlecha = resources.getString("btnFlecha.icon");
		Image imgFlecha = new Image(getClass().getResourceAsStream(rutaFlecha));
		ImageView viewFlecha = new ImageView(imgFlecha);
		viewFlecha.setFitWidth(80);
		viewFlecha.setFitHeight(40);
		btnFlecha.setGraphic(viewFlecha);

		// config img btn Admin
		String rutaAdmin = resources.getString("btnAdmin.icon");
		Image imgAdmin = new Image(getClass().getResourceAsStream(rutaAdmin));
		ImageView viewAdmin = new ImageView(imgAdmin);
		viewAdmin.setFitWidth(20);
		viewAdmin.setFitHeight(20);
		btnAdmin.setGraphic(viewAdmin);

		// config img btn Salir
		String rutaSalir = resources.getString("btnSalir.icon");
		Image imgSalir = new Image(getClass().getResourceAsStream(rutaSalir));
		ImageView viewSalir = new ImageView(imgSalir);
		viewSalir.setFitWidth(20);
		viewSalir.setFitHeight(20);
		btnSalir.setGraphic(viewSalir);

		// mnemónicos
		btnFlecha.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.E) {
				btnFlecha.fire(); // Simula el clic en el botón
				event.consume(); // Detiene la propagación del evento
			}
		});

		btnAdmin.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.A) {
				btnAdmin.fire();
				event.consume();
			}
		});

		btnSalir.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.S) {
				btnSalir.fire();
				event.consume();
			}
		});

		// tooltips
		btnFlecha.setTooltip(new Tooltip("Intro (Alt+E)"));
		btnAdmin.setTooltip(new Tooltip("Admin (Alt+A)"));
		btnSalir.setTooltip(new Tooltip("Salir (Alt+S)"));

	}

	// handler botones
	@FXML
	private void handlerFlecha(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	@FXML
	private void handlerAdmin(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.LOGINADMIN);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		Platform.exit();
	}

}
