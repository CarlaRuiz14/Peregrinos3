package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.BotonesConfig;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;
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
public class AdminController implements Initializable {

	@FXML
	private Label lblTitulo;

	@FXML
	private Button btnParada;

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnSalir;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private BotonesConfig botones;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// config img btn Logout
		botones.configImgLogout(btnLogout);

		// config img btn Salir
		botones.configImgSalir(btnSalir);

		// mnemónicos
		btnParada.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.N) {
				btnParada.fire();
				event.consume();
			}
		});

		btnLogout.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.L) {
				btnLogout.fire();
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
		btnParada.setTooltip(new Tooltip("Nueva Parada (Alt+N)"));
		btnLogout.setTooltip(new Tooltip("Logout (Alt+L)"));
		btnSalir.setTooltip(new Tooltip("Salir (Alt+S)"));

	}

	/**
	 * Handler para botón btnParada. Método que cambia la escena a la ventana de
	 * registrar nueva parada.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlerParada(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.REGPARADA);
	}

	/**
	 * Handler para botón Logout. Método que desactiva la sesión actual del usuario.
	 * Establece usuarioActivo a null y el perfilActivo a INVITADO. Cambia la escena
	 * a la ventana de Login.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlerLogout(ActionEvent event) throws IOException {

		usuarioService.configurarSesion(null, Perfil.INVITADO);

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
