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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

@Controller
public class EnvioController implements Initializable{

	@FXML
	private Hyperlink hpInfo;
	
	@FXML
	private TextField txtDireccion;
	
	@FXML
	private TextField txtLocalidad;
	
	@FXML
	private CheckBox chUrgente;
	
	@FXML
	private TextField txtPeso;
	
	@FXML
	private TextField txtAlto;
	
	@FXML
	private TextField txtAncho;
	
	@FXML
	private TextField txtFondo;
	
	@FXML
	private Button btnEnviar;
	
	@FXML
	private Button btnVolver;
	
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
	private Tooltips tooltipConfig;
	
	@Autowired
	private Mnemonic mnemonicConfig;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {		

		ayuda.configImgInfo(hpInfo);
		botones.imgFlecha(btnEnviar);
		botones.imgVolver(btnVolver);
		botones.imgSalir(btnSalir);
		
		mnemonicConfig.infoMnemonic(hpInfo);
		btnEnviar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
				btnEnviar.fire();
				event.consume();
			}
		});
		mnemonicConfig.volverMnemonic(btnVolver);
		mnemonicConfig.salirMnemonic(btnSalir);
		
		tooltipConfig.salirTooltip(btnSalir);
		btnEnviar.setTooltip(new Tooltip("Enviar (Alt+Enter)"));
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);
	}	
	
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
	    Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/administrador.html",stage);
	}
	
	@FXML
	private void handlerEnviar(ActionEvent event) throws IOException {
	  
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
