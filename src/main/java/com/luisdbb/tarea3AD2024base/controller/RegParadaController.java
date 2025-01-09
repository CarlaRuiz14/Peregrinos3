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
public class RegParadaController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private TextField txtUsuario;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtContraseña;

	@FXML
	private TextField txtConfirmacion;

	@FXML
	private TextField txtNombreP;

	@FXML
	private TextField txtRegionP;

	@FXML
	private Button btnLimpiar;

	@FXML
	private Button btnRegistrar;

	@FXML
	private Button btnVolver;

	@FXML
	private Label lblFeed;

	@FXML
	private Button btnSalir;

	// inyecciones
	@Lazy
	@Autowired
	private StageManager stageManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// config info
		String rutaInfo = resources.getString("info.icon");
		Image imagen = new Image(getClass().getResourceAsStream(rutaInfo));
		ImageView imageView = new ImageView(imagen);
		imageView.setFitWidth(30);
		imageView.setFitHeight(30);
		imageView.setPreserveRatio(true);
		hpInfo.setGraphic(imageView);

		// config img btn Limpiar
		String rutaLimp = resources.getString("btnLimpiar.icon");
		Image imgLimp = new Image(getClass().getResourceAsStream(rutaLimp));
		ImageView viewLimp = new ImageView(imgLimp);
		viewLimp.setFitWidth(30);
		viewLimp.setFitHeight(30);
		btnLimpiar.setGraphic(viewLimp);

		// config img btn Registrar
		String rutaReg = resources.getString("btnRegistrar.icon");
		Image imgReg = new Image(getClass().getResourceAsStream(rutaReg));
		ImageView viewReg = new ImageView(imgReg);
		viewReg.setFitWidth(60);
		viewReg.setFitHeight(30);
		btnRegistrar.setGraphic(viewReg);

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

		// mnemónicos
		hpInfo.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.I) {
				hpInfo.fire();
				event.consume();
			}
		});

		btnLimpiar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.L) {
				btnLimpiar.fire();
				event.consume();
			}
		});

		btnRegistrar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.R) {
				btnRegistrar.fire();
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
		btnLimpiar.setTooltip(new Tooltip("Limpiar (Alt+L)"));
		btnRegistrar.setTooltip(new Tooltip("Registrar (Alt+R)"));
		btnVolver.setTooltip(new Tooltip("Volver (Alt+V)"));
		btnSalir.setTooltip(new Tooltip("Salir (Alt+S)"));

	}

	// handler botones
	@FXML
	private void handlerRegistrar(ActionEvent event) throws IOException {

	}

	@FXML
	private void handlerLimpiar(ActionEvent event) throws IOException {

	}

	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.ADMIN);
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
