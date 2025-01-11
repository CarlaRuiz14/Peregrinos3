package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.Alertas;
import com.luisdbb.tarea3AD2024base.config.Perfil;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
public class LoginController implements Initializable {

	@FXML
	private ImageView imgUsuario;

	@FXML
	private TextField txtUsuario;

	@FXML
	private ImageView imgContraseña;

	@FXML
	private TextField txtContraseña;

	@FXML
	private Hyperlink hpContraseña;

	@FXML
	private Hyperlink hpRegistro;

	@FXML
	private Button btnLogin;

	@FXML
	private Label lblFeed;

	@FXML
	private Button btnVolver;

	@FXML
	private Button btnSalir;

	// inyecciones
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private Sesion sesion;

	@Lazy
	@Autowired
	private StageManager stageManager;

	// métodos
	public String getContraseña() {
		return txtContraseña.getText();
	}

	public String getUsuario() {
		return txtUsuario.getText();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// config img btn Login
		String rutaLogin = resources.getString("btnLogin.icon");
		Image imgLogin = new Image(getClass().getResourceAsStream(rutaLogin));
		ImageView viewLogin = new ImageView(imgLogin);
		viewLogin.setFitWidth(60);
		viewLogin.setFitHeight(30);
		btnLogin.setGraphic(viewLogin);

		// config img btn Volver
		String rutaVolver = resources.getString("btnVolver.icon");
		Image imgVolver = new Image(getClass().getResourceAsStream(rutaVolver));
		ImageView viewVolver = new ImageView(imgVolver);
		viewVolver.setFitWidth(20);
		viewVolver.setFitHeight(20);
		btnVolver.setGraphic(viewVolver);

		// config img btn Salir
		String rutaSalir = resources.getString("btnSalir.icon");
		Image imgSalir = new Image(getClass().getResourceAsStream(rutaSalir));
		ImageView viewSalir = new ImageView(imgSalir);
		viewSalir.setFitWidth(20);
		viewSalir.setFitHeight(20);
		btnSalir.setGraphic(viewSalir);

		// config imagenes
		String rutaUsu = resources.getString("usuario.icon");
		imgUsuario.setImage(new Image(getClass().getResourceAsStream(rutaUsu)));

		String rutaCon = resources.getString("contraseña.icon");
		imgContraseña.setImage(new Image(getClass().getResourceAsStream(rutaCon)));

		// mnemónicos
		hpContraseña.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.C) {
				hpContraseña.fire();
				event.consume();
			}
		});
		hpRegistro.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.R) {
				hpRegistro.fire();
				event.consume();
			}
		});
		btnLogin.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.L) {
				btnLogin.fire();
				event.consume();
			}
		});

		btnVolver.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.V) {
				btnVolver.fire();
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
		hpContraseña.setTooltip(new Tooltip("Recuperar (Alt+C)"));
		hpRegistro.setTooltip(new Tooltip("Registro (Alt+R)"));
		btnLogin.setTooltip(new Tooltip("Login (Alt+L)"));
		btnVolver.setTooltip(new Tooltip("Volver (Alt+V)"));
		btnSalir.setTooltip(new Tooltip("Salir (Alt+S)"));

	}

	// handler botones
	@FXML
	private void handlerLogin(ActionEvent event) throws IOException {
		lblFeed.setText(" ");
		if (getUsuario() == null || getContraseña() == null || getUsuario().isEmpty() || getContraseña().isEmpty()) {
			Alertas.alertaInformacion("Faltan datos", "Los campos usuario y contraseña son obligatorios");
		} else {
			Perfil perfilActivo = usuarioService.loguear(getUsuario(), getContraseña());
			switch (perfilActivo) {
			case PEREGRINO:
				// sesion
				sesion.setUsuarioActivo(usuarioService.findByUsuario(getUsuario()));
				sesion.setPerfilActivo(perfilActivo);

				stageManager.switchScene(FxmlView.PEREGRINO);
				break;
			case PARADA:
				// sesion
				sesion.setUsuarioActivo(usuarioService.findByUsuario(getUsuario()));
				sesion.setPerfilActivo(perfilActivo);

				stageManager.switchScene(FxmlView.PARADA);
				break;
			case ADMINISTRADOR:
				// sesion
				sesion.setUsuarioActivo(usuarioService.findByUsuario(getUsuario()));
				sesion.setPerfilActivo(perfilActivo);

				stageManager.switchScene(FxmlView.ADMIN);
				break;
			case null:
				Alertas.alertaInformacion("Datos no encontrados", "Los datos introducidos no están registrados.");
				break;
			default:
				lblFeed.setText("Error al hacer login");
			}
		}
	}

	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.MAIN);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		Platform.exit();
	}

	// handler hipervinculos
	@FXML
	private void handlerHpContraseña(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.RECUPERACION);
	}

	@FXML
	private void handlerHpRegistro(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.REGPEREGRINO);
	}

}
