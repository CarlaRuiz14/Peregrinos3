package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.Alertas;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
public class AlojarController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private Label lblNombre;

	@FXML
	private Label lblRegion;

	@FXML
	private Label lblId;

	@FXML
	private RadioButton rbtnSi;

	@FXML
	private RadioButton rbtnNo;

	ToggleGroup respuesta = new ToggleGroup();

	@FXML
	private Button btnAlojar;

	@FXML
	private Button btnVolver;

	@FXML
	private Button btnSalir;	

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// configuracion info
		String rutaInfo = resources.getString("info.icon");
		Image imagen = new Image(getClass().getResourceAsStream(rutaInfo));
		ImageView imageView = new ImageView(imagen);
		imageView.setFitWidth(30);
		imageView.setFitHeight(30);
		imageView.setPreserveRatio(true);
		hpInfo.setGraphic(imageView);

		// config toggle group

		rbtnSi.setToggleGroup(respuesta);
		rbtnNo.setToggleGroup(respuesta);

		// configuracion imagen boton Alojar
		String rutaAlojar = resources.getString("btnAlojar.icon");
		Image imgAlojar = new Image(getClass().getResourceAsStream(rutaAlojar));
		ImageView viewAlojar = new ImageView(imgAlojar);
		viewAlojar.setFitWidth(60);
		viewAlojar.setFitHeight(30);
		btnAlojar.setGraphic(viewAlojar);

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

		// mnenomicos
		hpInfo.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.I) {
				hpInfo.fire();
				event.consume();
			}
		});

		btnAlojar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.A) {
				btnAlojar.fire();
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
		btnAlojar.setTooltip(new Tooltip("Alojar (Alt+A)"));
		btnVolver.setTooltip(new Tooltip("Volver (Alt+V)"));
		btnSalir.setTooltip(new Tooltip("Salir (Alt+S)"));

	}

	// handler botones

	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {

	}

	@FXML
	private void handlerAlojar(ActionEvent event) {

		if (respuesta.getSelectedToggle() == null) {
			Alertas.alertaInformacion("Selección requerida", "Debe seleccionar una opción antes de continuar.");
			return;
		}

		RadioButton seleccion = (RadioButton) respuesta.getSelectedToggle();

		if (seleccion.equals(rbtnSi)) {

			Alertas.alertaInformacion("Alojar", "El peregrino será alojado.");
			stageManager.switchScene(FxmlView.VIP);

		} else if (seleccion.equals(rbtnNo)) {
			
			Alertas.alertaInformacion("No alojar", "El peregrino no será alojado.\nDatos guardados.\nVolviendo a su Menú.");
			stageManager.switchScene(FxmlView.PARADA);
		}
	}

	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.SELLAR);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		Platform.exit();
	}

}
