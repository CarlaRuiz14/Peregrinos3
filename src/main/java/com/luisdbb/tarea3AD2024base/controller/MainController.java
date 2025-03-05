package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * MainController es el controlador de la vista principal de la aplicación.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Controller
public class MainController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private Label lblTitulo;

	@FXML
	private Button btnFlecha;

	@FXML
	private Button btnSalir;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private Ayuda ayuda;

	@Autowired
	private Botones botones;

	@Autowired
	private Mnemonic mnemonicConfig;

	@Autowired
	private Tooltips tooltipConfig;

	/**
	 * Inicializa la vista principal.
	 * <ul>
	 * <li>Configura la imagen de ayuda.</li>
	 * <li>Asigna imágenes a los botones de navegación y salida.</li>
	 * <li>Configura atajos de teclado y tooltips para los elementos de la
	 * interfaz.</li>
	 * </ul>
	 *
	 * @param location  La URL utilizada para resolver el objeto raíz o null.
	 * @param resources Los recursos de localización o null.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ayuda.configImgInfo(hpInfo);
		botones.imgFlecha(btnFlecha);
		botones.imgSalir(btnSalir);
		mnemonicConfig.infoMnemonic(hpInfo);
		btnFlecha.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
				btnFlecha.fire();
				event.consume();
			}
		});
		mnemonicConfig.salirMnemonic(btnSalir);

		tooltipConfig.infoTooltip(hpInfo);
		btnFlecha.setTooltip(new Tooltip("Intro (Alt+Enter)"));
		tooltipConfig.salirTooltip(btnSalir);
	}

	/**
	 * Muestra la ayuda relacionada con la vista principal.
	 *
	 * @param event Evento de acción disparado al hacer clic en el enlace de ayuda.
	 * @throws IOException Si ocurre un error al cargar el recurso de ayuda.
	 */
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/main.html", stage);
	}

	/**
	 * Navega a la pantalla de login.
	 * 
	 * @param event Evento de acción disparado al hacer clic en el botón de
	 *              navegación.
	 * @throws IOException Si ocurre un error al cambiar de escena.
	 */
	@FXML
	private void handlerFlecha(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	/**
	 * Ejecuta la acción de salida de la aplicación.
	 *
	 * 
	 * @param event Evento de acción disparado al hacer clic en el botón de salida.
	 * @throws IOException Si ocurre un error durante la configuración de salida.
	 */
	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}
}
