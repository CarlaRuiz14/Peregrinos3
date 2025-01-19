package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.Alertas;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.DatosSellado;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

	@Autowired
	private Alertas alertas;

	@Autowired
	private DatosSellado datosSellado;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// cargar datos
		lblNombre.setText(datosSellado.getParada().getNombre());
		lblRegion.setText(String.valueOf(datosSellado.getParada().getRegion()));
		lblId.setText(String.valueOf(datosSellado.getParada().getId()));

		// config info
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

		// config img btn Alojar
		String rutaAlojar = resources.getString("btnAlojar.icon");
		Image imgAlojar = new Image(getClass().getResourceAsStream(rutaAlojar));
		ImageView viewAlojar = new ImageView(imgAlojar);
		viewAlojar.setFitWidth(60);
		viewAlojar.setFitHeight(30);
		btnAlojar.setGraphic(viewAlojar);

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

	/**
	 * Handler para el HyperLink hpInfo. Método que muestra el sistema de ayuda al
	 * pulsarlo.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		WebView webView = new WebView();

		String url = getClass().getResource("/help/help.html").toExternalForm();
		webView.getEngine().load(url);

		Stage helpStage = new Stage();
		helpStage.setTitle("Info");

		Scene helpScene = new Scene(webView, 600, 600);
		helpStage.setScene(helpScene);

		// Bloquear la ventana principal mientras se muestra la ayuda
		helpStage.initModality(Modality.APPLICATION_MODAL);
		helpStage.setResizable(false);

		helpStage.show();
	}

	@FXML
	private void handlerAlojar(ActionEvent event) {

		if (respuesta.getSelectedToggle() == null) {
			alertas.alertaInformacion("Selección requerida", "Debe seleccionar una opción antes de continuar.");
			return;
		}

		RadioButton seleccion = (RadioButton) respuesta.getSelectedToggle();

		if (seleccion.equals(rbtnSi)) {

			alertas.alertaInformacion("Alojar", "El peregrino será alojado.");
			stageManager.switchScene(FxmlView.VIP);

		} else if (seleccion.equals(rbtnNo)) {

			alertas.alertaInformacion("No alojar",
					"El peregrino no será alojado.\nDatos guardados.\n\nVolviendo a su Menú.");
			stageManager.switchScene(FxmlView.PARADA);
		}
	}

	/**
	 * Handler para el botón btnVolver. Método que al pulsarlo vuelve a la ventana
	 * de Login.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.SELLAR);
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
