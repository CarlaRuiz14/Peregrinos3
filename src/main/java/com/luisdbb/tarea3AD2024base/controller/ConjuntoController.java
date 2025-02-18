package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.DatosSellado;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.services.ConjuntoServicioService;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Controller
public class ConjuntoController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private CheckBox chVip;

	@FXML
	private TableView<Servicio> tblServicios;

	@FXML
	private TableColumn<Servicio, Long> colIdServicio;

	@FXML
	private TableColumn<Servicio, String> colNombreServicio;

	@FXML
	private TableColumn<Servicio, Double> colPrecioServicio;

	@FXML
	private Label lblTotal;	
	
	@FXML
	private RadioButton rbtnEfectivo;

	@FXML
	private RadioButton rbtnTarjeta;
	
	@FXML
	private RadioButton rbtnBizum;

	ToggleGroup pago = new ToggleGroup();

	@FXML
	private Button btnContratar;

	@FXML
	private Button btnVolver;

	@FXML
	private Button btnSalir;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private Alertas alertas;

	@Autowired
	private Botones botones;

	@Autowired
	private Ayuda ayuda;	

	@Autowired
	private Mnemonic mnemonicConfig;

	@Autowired
	private Tooltips tooltipConfig;
	
	@Autowired 
	private ConjuntoServicioService css;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		colIdServicio.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombreServicio.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colPrecioServicio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		
		tblServicios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		List<Servicio> listaS = css.findAllServicios();
		ObservableList<Servicio> listServicios = FXCollections.observableArrayList(listaS);
		tblServicios.setItems(listServicios);
		tblServicios.setPlaceholder(new Label("Sin Servicios"));
		
		tblServicios.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Servicio>) change -> {
		    double total = 0.0;
		    for (Servicio servicio : tblServicios.getSelectionModel().getSelectedItems()) {
		        total += servicio.getPrecio(); 
		    }
		   
		    lblTotal.setText(String.format("Total: %.2f", total));
		});		
		
		rbtnEfectivo.setToggleGroup(pago);
		rbtnTarjeta.setToggleGroup(pago);
		rbtnBizum.setToggleGroup(pago);

		ayuda.configImgInfo(hpInfo);	
		botones.imgFlecha(btnContratar);	
		botones.imgVolver(btnVolver);	
		botones.imgSalir(btnSalir);
	
		mnemonicConfig.infoMnemonic(hpInfo);
		btnContratar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
				btnContratar.fire();
				event.consume();
			}
		});
		mnemonicConfig.volverMnemonic(btnVolver);
		mnemonicConfig.salirMnemonic(btnSalir);
		
		tooltipConfig.salirTooltip(btnSalir);
		btnContratar.setTooltip(new Tooltip("Contratar (Alt+Enter)"));
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);
	}

	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/conjunto.html",stage);
	}

	@FXML
	private void handlerContratar(ActionEvent event) {
//		try {
//			if (respuesta.getSelectedToggle() == null) {
//				alertas.alertaInformacion("Selección requerida", "Debe seleccionar una opción antes de continuar.");
//				return;
//			}
//
//			RadioButton seleccion = (RadioButton) respuesta.getSelectedToggle();
//
//			if (seleccion.equals(rbtnSi)) {
//
//				estanciaService.registrarEstancia(true, datosSellado.getPeregrino(), datosSellado.getParada(),
//						datosSellado.getCarnet());
//
//				alertas.alertaInformacion("Estancia VIP",
//						"El peregrino ha contratado estancia VIP.\nDatos guardados.\n\nVolviendo a su Menú.");
//				stageManager.switchScene(FxmlView.PARADA);
//
//			} else if (seleccion.equals(rbtnNo)) {
//
//				estanciaService.registrarEstancia(false, datosSellado.getPeregrino(), datosSellado.getParada(),
//						datosSellado.getCarnet());
//
//				alertas.alertaInformacion("Estancia VIP",
//						"El peregrino no ha contratado la estancia VIP.\nDatos guardados.\n\nVolviendo a su Menú.");
//				stageManager.switchScene(FxmlView.PARADA);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			alertas.alertaError("Error", "Hubo un problema al registrar los datos. Por favor, revise la información.");
//			return;
//		}
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
