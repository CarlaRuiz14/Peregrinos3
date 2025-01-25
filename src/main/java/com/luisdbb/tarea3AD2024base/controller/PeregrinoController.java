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

/**
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// config img info
		ayuda.configImgInfo(hpInfo);

		// config img btn Logout
		botones.imgLogout(btnLogout);

		// config img btn Salir
		botones.imgSalir(btnSalir);

		// mnemónicos
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

		// tooltips
		tooltipConfig.salirTooltip(btnSalir);
		btnExportar.setTooltip(new Tooltip("Exportar Carnet (Alt+X)"));
		btnEditar.setTooltip(new Tooltip("Editar (Alt+E)"));
		tooltipConfig.logoutTooltip(btnLogout);
		tooltipConfig.salirTooltip(btnSalir);

	}

	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		ayuda.configInfo("/help/help.html");
	}

	@FXML
	private void handlerExportar(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.CARNET);
	}

	@FXML
	private void handlerEditar(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.EDITAR);
	}

	@FXML
	private void handlerLogout(ActionEvent event) throws IOException {
		botones.logoutConfig(usuarioService, stageManager);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}
}
