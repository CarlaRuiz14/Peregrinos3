package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */

@Controller
public class ParadaController implements Initializable {

	@FXML
	private Label lblTitulo;

	@FXML
	private Button btnExportar;

	@FXML
	private Button btnSellar;

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnSalir;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// config img btn Logout
		String rutaLog = resources.getString("btnLogout.icon");
		Image imgLogout = new Image(getClass().getResourceAsStream(rutaLog));
		ImageView viewLog = new ImageView(imgLogout);
		viewLog.setFitWidth(20);
		viewLog.setFitHeight(20);
		btnLogout.setGraphic(viewLog);

		// config img btn Salir
		String rutaSalir = resources.getString("btnSalir.icon");
		Image imgSalir = new Image(getClass().getResourceAsStream(rutaSalir));
		ImageView viewSalir = new ImageView(imgSalir);
		viewSalir.setFitWidth(20);
		viewSalir.setFitHeight(20);
		btnSalir.setGraphic(viewSalir);

		// mnemónicos
		btnExportar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.X) {
				btnExportar.fire();
				event.consume();
			}
		});

		btnSellar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.A) {
				btnSellar.fire();
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
		btnExportar.setTooltip(new Tooltip("Exportar Carnet (Alt+X)"));
		btnSellar.setTooltip(new Tooltip("Sellar/Alojar (Alt+A)"));
		btnLogout.setTooltip(new Tooltip("Logout (Alt+L)"));
		btnSalir.setTooltip(new Tooltip("Salir (Alt+S)"));

	}

	// handler botones
	@FXML
	private void handlerExportar(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.EXPPARADA);

	}

	@FXML
	private void handlerSellar(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.SELLAR);
	}

	/**
	 * Handler para botón Logout. Método que sale desactiva la sesión actual del
	 * usuario. Establece usuarioActivo a null y el perfilActivo a INVITADO. Cambia
	 * la escena a la ventana de Login.
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
