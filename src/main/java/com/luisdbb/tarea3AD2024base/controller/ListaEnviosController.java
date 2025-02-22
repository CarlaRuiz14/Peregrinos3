package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.EnvioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private TableColumn<EnvioACasa, double[]> colVolumen;
	
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
	
	@Autowired
	private EnvioService envioService;
	
	@Autowired
	private Sesion sesion;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		colIdEnvio.setCellValueFactory(new PropertyValueFactory<>("id"));
		colUrgente.setCellValueFactory(new PropertyValueFactory<>("urgente"));
		colUrgente.setCellFactory(tc -> new TableCell<EnvioACasa, Boolean>() {
			@Override
			protected void updateItem(Boolean urgente, boolean empty) {
				super.updateItem(urgente, empty);
				if (empty || urgente == null) {
					setText(null);
				} else {
					setText(urgente ? "Sí" : "No");
				}
			}
		});
		
		colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
		colVolumen.setCellValueFactory(new PropertyValueFactory<>("volumen"));
		colVolumen.setCellFactory(tc -> new TableCell<EnvioACasa, double[]>() {
		    @Override
		    protected void updateItem(double[] volumen, boolean empty) {
		        super.updateItem(volumen, empty);
		        if (empty || volumen == null) {
		            setText(null);
		        } else {
		            setText(String.format("%.2f x %.2f x %.2f", volumen[0], volumen[1], volumen[2]));
		        }
		    }
		});
		
		tblEnvios.setPlaceholder(new Label("No se han realizado envíos"));		
		
		List<EnvioACasa> envios = envioService.getEnvios(sesion.getUsuarioActivo().getId());
		ObservableList<EnvioACasa> listEnvios = FXCollections.observableArrayList(envios);
		tblEnvios.setItems(listEnvios);		
		
		tblEnvios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	txtDireccion.setText(newSelection.getDireccion().getDireccion());
		    	txtLocalidad.setText(newSelection.getDireccion().getLocalidad());		   
		    } else {
		        txtDireccion.clear();
		        txtLocalidad.clear();
		    }
		});	
		
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
		ayuda.configInfo("/help/listaEnvios.html", stage);
	}
	
	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.PARADA);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}
	
}
