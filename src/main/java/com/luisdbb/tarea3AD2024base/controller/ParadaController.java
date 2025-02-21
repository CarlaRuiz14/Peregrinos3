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

	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/parada.html",stage);
	}

	@FXML
	private void handlerExportar(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.EXPPARADA);
	}

	@FXML
	private void handlerSellar(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.SELLAR);
	}
	
	@FXML
	private void handlerEnvios(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.LISTAENVIOS);
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
