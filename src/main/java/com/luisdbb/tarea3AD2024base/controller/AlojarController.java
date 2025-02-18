package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.DatosSellado;
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
	private Ayuda ayuda;

	@Autowired
	private Botones botones;

	@Autowired
	private DatosSellado datosSellado;

	@Autowired
	private Mnemonic mnemonicConfig;

	@Autowired
	private Tooltips tooltipConfig;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		lblNombre.setText(datosSellado.getParada().getNombre());
		lblRegion.setText(String.valueOf(datosSellado.getParada().getRegion()));
		lblId.setText(String.valueOf(datosSellado.getParada().getId()));
	
		rbtnSi.setToggleGroup(respuesta);
		rbtnNo.setToggleGroup(respuesta);

		ayuda.configImgInfo(hpInfo);		
		botones.imgFlecha(btnAlojar);	
		botones.imgVolver(btnVolver);		
		botones.imgSalir(btnSalir);

		
		mnemonicConfig.infoMnemonic(hpInfo);
		btnAlojar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
				btnAlojar.fire();
				event.consume();
			}
		});
		mnemonicConfig.volverMnemonic(btnVolver);
		mnemonicConfig.salirMnemonic(btnSalir);
		
		tooltipConfig.infoTooltip(hpInfo);
		btnAlojar.setTooltip(new Tooltip("Alojar (Alt+Enter)"));
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);
	}

	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/alojar.html",stage);
	}

	@FXML
	private void handlerAlojar(ActionEvent event) {

		if (respuesta.getSelectedToggle() == null) {
			alertas.alertaInformacion("Selección requerida", "Debe seleccionar una opción antes de continuar.");
			return;
		}

		RadioButton seleccion = (RadioButton) respuesta.getSelectedToggle();

		if (seleccion.equals(rbtnSi)) {
			alertas.alertaInformacion("Alojar",
					"El peregrino " + datosSellado.getPeregrino().getNombre() + " será alojado.");
			stageManager.switchScene(FxmlView.CONJUNTO);
		} else if (seleccion.equals(rbtnNo)) {
			alertas.alertaInformacion("No alojar", "El peregrino " + datosSellado.getPeregrino().getNombre()
					+ " no será alojado.\nDatos guardados.\n\nVolviendo a su Menú.");
			stageManager.switchScene(FxmlView.PARADA);
		}
	}

	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.SELLAR);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}
}
