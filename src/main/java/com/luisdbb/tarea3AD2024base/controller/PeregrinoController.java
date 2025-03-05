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
 * PeregrinoController es el controlador para la vista del usuario peregrino.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Controller
public class PeregrinoController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private Label lblTitulo;

	@FXML
	private Button btnExportar;

	@FXML
	private Button btnEditar;

	@FXML
	private Button btnLogout;

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
	private UsuarioService usuarioService;

	@Autowired
	private Mnemonic mnemonicConfig;

	@Autowired
	private Tooltips tooltipConfig;

	/**
	 * Inicializa la vista del controlador del peregrino.
	 * <ul>
	 * <li>Configura la imagen de ayuda y los iconos de logout y salida.</li>
	 * <li>Asigna atajos de teclado para exportar y editar, y configura el logout y
	 * la salida.</li>
	 * <li>Establece tooltips descriptivos para cada botón.</li>
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
		btnEditar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.E) {
				btnEditar.fire();
				event.consume();
			}
		});
		mnemonicConfig.logoutMnemonic(btnLogout);
		mnemonicConfig.salirMnemonic(btnSalir);

		tooltipConfig.salirTooltip(btnSalir);
		btnExportar.setTooltip(new Tooltip("Exportar Carnet (Alt+X)"));
		btnEditar.setTooltip(new Tooltip("Editar (Alt+E)"));
		tooltipConfig.logoutTooltip(btnLogout);
		tooltipConfig.salirTooltip(btnSalir);
	}

	/**
	 * Muestra la ayuda relacionada con la vista del peregrino.
	 * 
	 * @param event Evento de acción disparado al hacer clic en el enlace de ayuda.
	 * @throws IOException Si ocurre un error al cargar el recurso de ayuda.
	 */
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/peregrino.html", stage);
	}

	/**
	 * Navega a la vista del carnet.
	 *
	 * @param event Evento de acción disparado al hacer clic en "Exportar".
	 * @throws IOException Si ocurre un error al cambiar de escena.
	 */
	@FXML
	private void handlerExportar(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.CARNET);
	}

	/**
	 * Navega a la vista de edición de datos.
	 *
	 * @param event Evento de acción disparado al hacer clic en "Editar".
	 * @throws IOException Si ocurre un error al cambiar de escena.
	 */
	@FXML
	private void handlerEditar(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.EDITAR);
	}

	/**
	 * Realiza el logout del usuario.
	 * 
	 * @param event Evento de acción disparado al hacer clic en "Logout".
	 * @throws IOException Si ocurre un error durante el proceso de logout.
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
