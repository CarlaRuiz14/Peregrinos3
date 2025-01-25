package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.DatosSellado;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

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
	private Botones botones;

	@Autowired
	private Ayuda ayuda;

	@Autowired
	private DatosSellado datosSellado;

	@Autowired
	private EstanciaService estanciaService;

	@Autowired
	private Mnemonic mnemonicConfig;

	@Autowired
	private Tooltips tooltipConfig;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// cargar datos
		lblNombre.setText(datosSellado.getParada().getNombre());
		lblRegion.setText(String.valueOf(datosSellado.getParada().getRegion()));
		lblId.setText(String.valueOf(datosSellado.getParada().getId()));

		// config toggle group
		rbtnSi.setToggleGroup(respuesta);
		rbtnNo.setToggleGroup(respuesta);

		// config info
		ayuda.configImgInfo(hpInfo);

		// config img btn Vip
		botones.imgFlecha(btnVip);

		// config img btn Volver
		botones.imgVolver(btnVolver);

		// config img btn Salir
		botones.imgSalir(btnSalir);

		// mnemónicos
		mnemonicConfig.infoMnemonic(hpInfo);

		btnVip.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
				btnVip.fire();
				event.consume();
			}
		});

		mnemonicConfig.volverMnemonic(btnVolver);

		mnemonicConfig.salirMnemonic(btnSalir);

		// tooltips
		tooltipConfig.salirTooltip(btnSalir);
		btnVip.setTooltip(new Tooltip("Vip (Alt+Enter)"));
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);

	}

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

	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.ALOJAR);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}
}
