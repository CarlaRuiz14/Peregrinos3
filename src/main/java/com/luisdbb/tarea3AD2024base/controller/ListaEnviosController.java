package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Controller
public class ListaEnviosController implements Initializable{
	
	@FXML
	private Hyperlink hpInfo;	
	
	@FXML
	private TableView<EnvioACasa> tblEnvios;

	@FXML
	private TableColumn<EnvioACasa, Long> colIdEnvio;

	@FXML
	private TableColumn<EnvioACasa, Boolean> colUrgente;

	@FXML
	private TableColumn<EnvioACasa, Double> colPeso;	
	
	@FXML
	private TableColumn<EnvioACasa, int[]> colVolumen;
	
	@FXML
	private TextField txtDireccion;
	
	@FXML
	private TextField txtLocalidad;
	
	@FXML
	private Button btnVolver;

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
	private Mnemonic mnemonicConfig;

	@Autowired
	private Tooltips tooltipConfig;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		
		
		ayuda.configImgInfo(hpInfo);
		botones.imgVolver(btnVolver);
		botones.imgSalir(btnSalir);

		mnemonicConfig.infoMnemonic(hpInfo);	
		mnemonicConfig.volverMnemonic(btnVolver);
		mnemonicConfig.salirMnemonic(btnSalir);

		tooltipConfig.salirTooltip(btnSalir);
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);
	}	
	
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/conjunto.html", stage);
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
