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

	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/main.html",stage);
	}

	@FXML
	private void handlerFlecha(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}
}
