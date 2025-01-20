package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.Alertas;
import com.luisdbb.tarea3AD2024base.config.AyudaConfig;
import com.luisdbb.tarea3AD2024base.config.BotonesConfig;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.DatosSellado;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Controller
public class VipController implements Initializable {

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
	private Button btnVip;

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
	private BotonesConfig botones;

	@Autowired
	private AyudaConfig ayuda;

	@Autowired
	private DatosSellado datosSellado;

	@Autowired
	private EstanciaService estanciaService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// cargar datos
		lblNombre.setText(datosSellado.getParada().getNombre());
		lblRegion.setText(String.valueOf(datosSellado.getParada().getRegion()));
		lblId.setText(String.valueOf(datosSellado.getParada().getId()));

		// config info
		ayuda.configImgInfo(hpInfo);

		// config toggle group
		rbtnSi.setToggleGroup(respuesta);
		rbtnNo.setToggleGroup(respuesta);

		// config img btn Vip
		botones.configImgFlecha(btnVip);

		// config img btn Volver
		botones.configImgVolver(btnVolver);

		// config img btn Salir
		botones.configImgSalir(btnSalir);

		// mnemónicos
		hpInfo.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.I) {
				hpInfo.fire();
				event.consume();
			}
		});

		btnVip.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.X) {
				btnVip.fire();
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
		btnVip.setTooltip(new Tooltip("Vip (Alt+X)"));
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
		ayuda.configInfo("/help/help.html");
	}

	@FXML
	private void handlerVip(ActionEvent event) {

		try {
			if (respuesta.getSelectedToggle() == null) {
				alertas.alertaInformacion("Selección requerida", "Debe seleccionar una opción antes de continuar.");
				return;
			}

			RadioButton seleccion = (RadioButton) respuesta.getSelectedToggle();

			if (seleccion.equals(rbtnSi)) {

				estanciaService.registrarEstancia(true, datosSellado.getPeregrino(), datosSellado.getParada(),
						datosSellado.getCarnet());

				alertas.alertaInformacion("Estancia VIP",
						"El peregrino ha contratado estancia VIP.\nDatos guardados.\n\nVolviendo a su Menú.");
				stageManager.switchScene(FxmlView.PARADA);

			} else if (seleccion.equals(rbtnNo)) {

				estanciaService.registrarEstancia(false, datosSellado.getPeregrino(), datosSellado.getParada(),
						datosSellado.getCarnet());

				alertas.alertaInformacion("Estancia VIP",
						"El peregrino no ha contratado la estancia VIP.\nDatos guardados.\n\nVolviendo a su Menú.");
				stageManager.switchScene(FxmlView.PARADA);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			alertas.alertaError("Error", "Hubo un problema al registrar los datos. Por favor, revise la información.");

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
		stageManager.switchScene(FxmlView.ALOJAR);
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
