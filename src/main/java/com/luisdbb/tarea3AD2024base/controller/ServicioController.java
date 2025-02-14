package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

@Component
public class ServicioController implements Initializable{	
	
	@FXML
	private Hyperlink hpInfo;
	
	@FXML
	private TableView tblServicios;
	
	@FXML
	private TableColumn<Servicio, Long> colIdServicio;
	
	@FXML
	private TableColumn<Servicio, String> colNombreServicio;
	
	@FXML
	private TableColumn<Servicio, Double> colPrecioServicio;
	
	@FXML
	private TableView tblParadas;
	
	@FXML
	private TableColumn<Parada, Long> colIdParada;
	
	@FXML
	private TableColumn<Parada, String> colNombreParada;
	
	@FXML
	private TableColumn<Parada, Character> colIdRegionParada;
	
	@FXML
	private TableColumn<Parada, Boolean> colServicioParada;
	
	@FXML
	private Button btnNuevo;
	
	@FXML
	private Button btnGuardar;
	
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
	private Mnemonic mnemonicConfig;
	
	@Autowired
	private Tooltips tooltipConfig;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {			
	
		List<Servicio> lServicios ;
		ObservableList<Servicio> listaServicios;
	//	tblServicios.setItems(listaServicios);
		tblParadas.setPlaceholder(new Label("Sin servicios"));

		
		
		
		
		
		
		ayuda.configImgInfo(hpInfo);		
		botones.imgFlecha(btnGuardar);	
		botones.imgVolver(btnVolver);		
		botones.imgSalir(btnSalir);
		
		mnemonicConfig.infoMnemonic(hpInfo);
		btnNuevo.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.N) {
				btnNuevo.fire();
				event.consume();
			}
		});		
		btnGuardar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.G) {
				btnGuardar.fire();
				event.consume();
			}
		});
		mnemonicConfig.volverMnemonic(btnVolver);
		mnemonicConfig.salirMnemonic(btnSalir);

		tooltipConfig.infoTooltip(hpInfo);
		btnNuevo.setTooltip(new Tooltip("Nuevo (Alt+N)"));
		btnGuardar.setTooltip(new Tooltip("Guardar (Alt+G)"));
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);		
		
	}	
	
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/servicios.html",stage);
	}
	
	@FXML
	private void handlerNuevo(ActionEvent event) throws IOException {
		
	}
	
	@FXML
	private void handlerGuardar(ActionEvent event) throws IOException {
		
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
