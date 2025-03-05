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
 * ParadaController es el controlador para la vista de Parada.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Controller
public class ParadaController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private Label lblTitulo;

	@FXML
	private Button btnExportar;

	@FXML
	private Button btnSellar;

	@FXML
	private Button btnEnvios;

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnSalir;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private Botones botones;

	@Autowired
	private Ayuda ayuda;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private Mnemonic mnemonicConfig;

	@Autowired
	private Tooltips tooltipConfig;

	/**
	 * Inicializa el controlador de la vista de Parada.
	 * <ul>
	 * <li>Configura la imagen de ayuda.</li>
	 * <li>Asigna imágenes a los botones de logout y salida.</li>
	 * <li>Configura atajos de teclado para los botones de exportar y sellar.</li>
	 * <li>Establece tooltips para exportar, sellar, logout y salir.</li>
	 * </ul>
	 *
	 * @param location  La URL utilizada para resolver el objeto raíz o null.
	 * @param resources Los recursos de localización o null.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ayuda.configImgInfo(hpInfo);
		botones.imgLogout(btnLogout);
		botones.imgSalir(btnSalir);

		mnemonicConfig.infoMnemonic(hpInfo);
		btnExportar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.X) {
				btnExportar.fire();
				event.consume();
			}
		});
		btnSellar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.A) {
				btnSellar.fire();
				event.consume();
			}
		});
		mnemonicConfig.logoutMnemonic(btnLogout);
		mnemonicConfig.salirMnemonic(btnSalir);

		tooltipConfig.salirTooltip(btnSalir);
		btnExportar.setTooltip(new Tooltip("Exportar Carnet (Alt+X)"));
		btnSellar.setTooltip(new Tooltip("Sellar/Alojar (Alt+A)"));
		tooltipConfig.logoutTooltip(btnLogout);
		tooltipConfig.salirTooltip(btnSalir);
	}

	/**
	 * Muestra la ayuda relacionada con la vista de Parada.
	 *
	 * @param event Evento de acción disparado al hacer clic en el enlace de ayuda.
	 * @throws IOException Si ocurre un error al cargar la ayuda.
	 */
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/parada.html", stage);
	}

	/**
	 * Navega a la vista de exportación de Parada.
	 *
	 * @param event Evento de acción disparado al hacer clic en "Exportar".
	 * @throws IOException Si ocurre un error al cambiar de escena.
	 */
	@FXML
	private void handlerExportar(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.EXPPARADA);
	}

	/**
	 * Navega a la vista de sellado o alojamiento.
	 *
	 * @param event Evento de acción disparado al hacer clic en "Sellar".
	 * @throws IOException Si ocurre un error al cambiar de escena.
	 */
	@FXML
	private void handlerSellar(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.SELLAR);
	}

	/**
	 * Navega a la vista de lista de envíos.
	 * 
	 * @param event Evento de acción disparado al hacer clic en "Envios".
	 * @throws IOException Si ocurre un error al cambiar de escena.
	 */
	@FXML
	private void handlerEnvios(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.LISTAENVIOS);
	}

	/**
	 * Realiza el logout del usuario.
	 *
	 * @param event Evento de acción disparado al hacer clic en "Logout".
	 * @throws IOException Si ocurre un error durante el logout.
	 */
	@FXML
	private void handlerLogout(ActionEvent event) throws IOException {
		botones.logoutConfig(usuarioService, stageManager);
	}

	/**
	 * Ejecuta la acción de salida de la aplicación.
	 *
	 * @param event Evento de acción disparado al hacer clic en "Salir".
	 * @throws IOException Si ocurre un error durante la salida.
	 */
	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}
}
