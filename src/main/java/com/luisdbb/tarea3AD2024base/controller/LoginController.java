package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
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
	// corregir boton login

	@FXML
	private Label lblTitulo;

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

	// mirar
	@Autowired
	private UsuarioService userService;

	@Lazy
	@Autowired
	private StageManager stageManager;

	public String getContraseña() {
		return txtContraseña.getText();
	}

	public String getUsuario() {
		return txtUsuario.getText();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// configuracion imagen boton Login
		String rutaLogin = resources.getString("btnLogin.icon");
		Image imgLogin = new Image(getClass().getResourceAsStream(rutaLogin));
		ImageView viewLogin = new ImageView(imgLogin);
		viewLogin.setFitWidth(60);
		viewLogin.setFitHeight(30);
		btnLogin.setGraphic(viewLogin);

		// configuracion imagen boton Volver
		String rutaVolver = resources.getString("btnVolver.icon");
		Image imgVolver = new Image(getClass().getResourceAsStream(rutaVolver));
		ImageView viewVolver = new ImageView(imgVolver);
		viewVolver.setFitWidth(20);
		viewVolver.setFitHeight(20);
		btnVolver.setGraphic(viewVolver);

		// configuracion imagen boton Salir
		String rutaSalir = resources.getString("btnSalir.icon");
		Image imgSalir = new Image(getClass().getResourceAsStream(rutaSalir));
		ImageView viewSalir = new ImageView(imgSalir);
		viewSalir.setFitWidth(20);
		viewSalir.setFitHeight(20);
		btnSalir.setGraphic(viewSalir);

		// configuracion imagenes
		String rutaUsu = resources.getString("usuario.icon");
		imgUsuario.setImage(new Image(getClass().getResourceAsStream(rutaUsu)));

		String rutaCon = resources.getString("contraseña.icon");
		imgContraseña.setImage(new Image(getClass().getResourceAsStream(rutaCon)));

		// mnenomicos
		hpContraseña.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.C) {
				btnLogin.fire();
				event.consume();
			}
		});
		hpRegistro.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.R) {
				btnLogin.fire();
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
		if (userService.authenticate(getUsuario(), getContraseña())) {

			stageManager.switchScene(FxmlView.USER);

		} else {
			lblTitulo.setText("Login Failed.");
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
