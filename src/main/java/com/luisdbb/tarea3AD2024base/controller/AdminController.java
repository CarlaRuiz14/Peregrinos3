package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
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
public class AdminController implements Initializable {

	@FXML
	private Label lblTitulo;

	@FXML
	private Button btnParada;

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnSalir;

	// inyecciones
	@Autowired
	private Sesion sesion;

	@Lazy
	@Autowired
	private StageManager stageManager;

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

	// handler botones
	@FXML
	private void handlerParada(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.REGPARADA);
	}

	@FXML
	private void handlerLogout(ActionEvent event) throws IOException {

		// sesión cerrada
		sesion.setUsuarioActivo(null);
		sesion.setPerfilActivo(Perfil.INVITADO);

		stageManager.switchScene(FxmlView.LOGIN);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		Platform.exit();
	}

}
