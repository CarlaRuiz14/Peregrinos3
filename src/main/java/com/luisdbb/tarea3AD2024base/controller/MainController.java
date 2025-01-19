package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.BotonesConfig;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */

@Controller
public class MainController implements Initializable {

	@FXML
	private Label lblTitulo;

	@FXML
	private Button btnFlecha;

	@FXML
	private Button btnSalir;

	// inyecciones
	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private BotonesConfig botones;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// config img btn flecha
		botones.configImgFlecha(btnFlecha);

		// config img btn Salir
		botones.configImgSalir(btnSalir);

		// mnemónicos
		btnFlecha.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.E) {
				btnFlecha.fire(); // Simula el clic en el botón
				event.consume(); // Detiene la propagación del evento
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
		btnSalir.setTooltip(new Tooltip("Salir (Alt+S)"));

	}

	/**
	 * Handler botón btnFlecha. Método que al pulsarlo cambia a la ventana de Login.
	 */
	@FXML
	private void handlerFlecha(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	/**
	 * Handler para botón btnSalir. Método que sale de la aplicación al pulsarlo.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		Platform.exit();
	}

}
