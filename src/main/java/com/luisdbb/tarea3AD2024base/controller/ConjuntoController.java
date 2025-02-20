package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.DatosSellado;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
	private ComboBox<String> cmbPago;

	ObservableList<String> listaPago;

	@FXML
	private TextArea txaNotas;

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
	private EstanciaService estanciaService;

	@Autowired
	private DatosSellado datosSellado;

	@Autowired
	private ConjuntoServicioService css;
	
	@Autowired
	private Sesion sesion;

	Map<Character, String> mapPago = new HashMap<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		colIdServicio.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombreServicio.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colPrecioServicio.setCellValueFactory(new PropertyValueFactory<>("precio"));

		tblServicios.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		Set<Servicio> listaS = css.findServiciosByIdParada(sesion.getUsuarioActivo().getId());
		ObservableList<Servicio> listServicios = FXCollections.observableArrayList(listaS);
		tblServicios.setItems(listServicios);
		tblServicios.setPlaceholder(new Label("Sin Servicios"));
		
		tblServicios.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Servicio>) change -> {
		    boolean seleccionVacia = tblServicios.getSelectionModel().getSelectedItems().isEmpty();
		    cmbPago.setDisable(seleccionVacia);
		});

		tblServicios.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Servicio>) change -> {
			double total = 0.0;
			for (Servicio servicio : tblServicios.getSelectionModel().getSelectedItems()) {
				total += servicio.getPrecio();
			}

			lblTotal.setText(String.format("%.2f", total));
		});

		mapPago.put('E', "Efectivo");
		mapPago.put('T', "Tarjeta");
		mapPago.put('B', "Bizum");
		listaPago = FXCollections.observableArrayList(mapPago.values());
		cmbPago.setItems(listaPago);

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
		ayuda.configInfo("/help/conjunto.html", stage);
	}

	@FXML
	private void handlerContratar(ActionEvent event) {
		try {
			if (!tblServicios.getSelectionModel().getSelectedItems().isEmpty()&&cmbPago.getSelectionModel().getSelectedItem().isEmpty()) {
				alertas.alertaError("Error", "Por favor seleccione un método de pago.");
				return;
			}

			Estancia estancia = guardarEstancia();
			if (estancia == null) {
				alertas.alertaError("Error", "No se pudo registrar la estancia. No se realizará la contratación.");
				return;
			}

			boolean correcto = saveConjunto(estancia);
			if (!correcto) {
				alertas.alertaError("Error",
						"Se registró la estancia, pero hubo un problema al registrar los servicios.");
			} else {
				alertas.alertaInformacion("Contrato Exitoso",
						"La estancia y los servicios han sido registrados correctamente.");
				stageManager.switchScene(FxmlView.PARADA);
			}

		} catch (Exception e) {
			e.printStackTrace();
			alertas.alertaError("Error", "No se han pordido guardar los datos correctamente.");
		}

	}

	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.ALOJAR);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}

	private Estancia guardarEstancia() {
		try {
			return estanciaService.registrarEstancia(chVip.isSelected(), datosSellado.getPeregrino(),
					datosSellado.getParada(), datosSellado.getCarnet());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean saveConjunto(Estancia estancia) {
		try {
			ConjuntoContratado conjunto = new ConjuntoContratado();
			conjunto.setId(0);
			conjunto.setPrecioTotal(Double.parseDouble(lblTotal.getText().replace(",", ".")));
			boolean seleccionVacia=tblServicios.getSelectionModel().getSelectedItems().isEmpty();
			conjunto.setModoPago(seleccionVacia?'-':getKeyPago(mapPago, cmbPago.getSelectionModel().getSelectedItem()));
			conjunto.setExtra(txaNotas.getText().isEmpty() ? null : txaNotas.getText());
			conjunto.setIdEstancia(estancia.getId());
			List<Servicio> listaSeleccion = new ArrayList<Servicio>(tblServicios.getSelectionModel().getSelectedItems());
			List<Long> listaIds = new ArrayList<>();
			for (Servicio servicio : listaSeleccion) {
				listaIds.add(servicio.getId());
			}
			conjunto.setListaServicios(listaIds);
			css.saveConjunto(conjunto);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private Character getKeyPago(Map<Character, String> mapPago, String valor) {
		for (Entry<Character, String> entry : mapPago.entrySet()) {
			if (entry.getValue().equals(valor)) {
				return entry.getKey();
			}
		}
		return null;
	}

}
