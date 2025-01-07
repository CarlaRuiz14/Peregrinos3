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
public class RecuperacionController implements Initializable {

	@FXML
	private Label lblTitulo;

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private TextField txtUsuario;

	@FXML
	private Label email;

	@FXML
	private Button btnEnviar;

	@FXML
	private Button btnVolver;

	@FXML
	private Label lblFeed;
	
	@FXML
	private Button btnSalir;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

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

		// configuracion imagen info
		String rutaInfo = resources.getString("info.icon");
		Image imagen = new Image(getClass().getResourceAsStream(rutaInfo));
		ImageView imageView = new ImageView(imagen);
		imageView.setFitWidth(50);
		imageView.setFitHeight(50);
		imageView.setPreserveRatio(true);
		hpInfo.setGraphic(imageView);

		// mnenomicos
		
		hpInfo.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.I) {
				hpInfo.fire(); 
				event.consume();
			}
		});
		
		btnEnviar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.E) {
				btnEnviar.fire(); 
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
		hpInfo.setTooltip(new Tooltip("Info (Alt+I)"));
		btnEnviar.setTooltip(new Tooltip("Enviar (Alt+E)"));
		btnVolver.setTooltip(new Tooltip("Volver (Alt+V)"));
		btnSalir.setTooltip(new Tooltip("Salir (Alt+S)"));

	}

	// handler botones

	@FXML
	private void handlerEnviar(ActionEvent event) throws IOException {

	}

	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		Platform.exit();
	}

	// handler info
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {

	}

}
