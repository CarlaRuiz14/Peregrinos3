package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.web.WebView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.Alertas;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

	// handler botones
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		 try {
		        // Crear un WebView para mostrar la ayuda
		        WebView webView = new WebView();

		        // Cargar el archivo HTML desde los recursos
		        String url = getClass().getResource("/help/help.html").toExternalForm();
		        webView.getEngine().load(url);

		        // Crear un nuevo Stage para la ventana de ayuda
		        Stage helpStage = new Stage();
		        helpStage.setTitle("Info");

		        // Crear una Scene con el WebView
		        Scene helpScene = new Scene(webView, 600, 600);

		        // Configurar la ventana
		        helpStage.setScene(helpScene);

		        // Bloquear la ventana principal mientras se muestra la ayuda
		        helpStage.initModality(Modality.APPLICATION_MODAL);
		        helpStage.setResizable(true);

		        // Mostrar la ventana de ayuda
		        helpStage.show();
		    } catch (NullPointerException e) {
		        // Manejar el caso en que el archivo de ayuda no se encuentra
		        Alert alert = new Alert(Alert.AlertType.ERROR);
		        alert.setTitle("Error");
		        alert.setHeaderText("Archivo de Ayuda no encontrado");
		        alert.setContentText("Por favor, verifica que el archivo 'help.html' esté en la ruta '/ayuda' dentro de los recursos del proyecto.");
		        alert.showAndWait();
		    }
		
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

			Alertas.alertaInformacion("No alojar",
					"El peregrino no será alojado.\nDatos guardados.\nVolviendo a su Menú.");
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
