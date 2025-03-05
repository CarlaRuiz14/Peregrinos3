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
 * Controlador para la contratación de servicios asociados a una estancia.
 * 
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

	/**
	 * Inicializa el controlador y configura la interfaz.
	 * 
	 * Se realiza lo siguiente:
	 * <ul>
	 * <li>Configurar las columnas de la tabla de servicios.</li>
	 * <li>Establecer la selección múltiple en la tabla.</li>
	 * <li>Cargar los servicios disponibles y mostrarlos en la tabla.</li>
	 * <li>Configurar listeners para actualizar el método de pago y el total.</li>
	 * <li>Inicializar el ComboBox de métodos de pago.</li>
	 * <li>Configurar imágenes, tooltips y atajos de teclado.</li>
	 * </ul>
	 *
	 * @param location  La URL para resolver el objeto raíz, o null.
	 * @param resources Los recursos de localización, o null.
	 */
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

	/**
	 * Muestra la información de ayuda para la contratación.
	 *
	 * @param event Evento disparado al hacer clic en el enlace de información.
	 * @throws IOException Si ocurre un error al cargar el recurso de ayuda.
	 */
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/conjunto.html", stage);
	}

	/**
	 * Gestiona la acción de contratación.
	 * 
	 * Valida la selección de servicios y método de pago, guarda la estancia y los
	 * servicios contratados, y redirige la vista según corresponda.
	 *
	 * @param event Evento disparado al hacer clic en el botón de contratar.
	 */
	@FXML
	private void handlerContratar(ActionEvent event) {
		try {

			List<Servicio> listaSeleccion = tblServicios.getSelectionModel().getSelectedItems();

			if (!listaSeleccion.isEmpty() && cmbPago.getSelectionModel().getSelectedItem() == null) {
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

				if (listaSeleccion.contains(css.getServicioByName("Envío a casa"))) {
					alertas.alertaInformacion("Envío a casa",
							"Ha sido contratado el servicio de envío a casa.\nPor favor, continúe para completar los detalles del envío.");
					stageManager.switchScene(FxmlView.ENVIO);

				} else {
					alertas.alertaInformacion("Contrato Exitoso",
							"La estancia y los servicios han sido registrados correctamente.");
					stageManager.switchScene(FxmlView.PARADA);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			alertas.alertaError("Error", "No se han pordido guardar los datos correctamente.");
		}

	}

	/**
	 * Regresa a la pantalla anterior.
	 *
	 * @param event Evento disparado al hacer clic en el botón de volver.
	 * @throws IOException Si ocurre un error al cambiar la escena.
	 */
	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.ALOJAR);
	}

	/**
	 * Sale de la aplicación.
	 *
	 * @param event Evento disparado al hacer clic en el botón de salir.
	 * @throws IOException Si ocurre un error durante la configuración de salida.
	 */
	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}

	/**
	 * Guarda la estancia en el sistema.
	 *
	 * @return La estancia registrada o null si ocurre un error.
	 */
	private Estancia guardarEstancia() {
		try {
			return estanciaService.registrarEstancia(chVip.isSelected(), datosSellado.getPeregrino(),
					datosSellado.getParada(), datosSellado.getCarnet());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Registra el conjunto de servicios contratados para la estancia.
	 *
	 * @param estancia La estancia a la que se asocian los servicios.
	 * @return true si se registra correctamente, false en caso contrario.
	 */
	private boolean saveConjunto(Estancia estancia) {
		try {
			ConjuntoContratado conjunto = new ConjuntoContratado();
			conjunto.setId(0);
			conjunto.setPrecioTotal(Double.parseDouble(lblTotal.getText().replace(",", ".")));
			boolean seleccionVacia = tblServicios.getSelectionModel().getSelectedItems().isEmpty();
			conjunto.setModoPago(
					seleccionVacia ? '-' : getKeyPago(mapPago, cmbPago.getSelectionModel().getSelectedItem()));
			conjunto.setExtra(txaNotas.getText().isEmpty() ? null : txaNotas.getText());
			conjunto.setIdEstancia(estancia.getId());
			List<Servicio> listaSeleccion = new ArrayList<Servicio>(
					tblServicios.getSelectionModel().getSelectedItems());
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

	/**
	 * Obtiene la clave correspondiente a un método de pago a partir de su valor.
	 *
	 * @param mapPago El mapa de métodos de pago.
	 * @param valor   El valor del método de pago.
	 * @return La clave del método de pago o null si no se encuentra.
	 */
	private Character getKeyPago(Map<Character, String> mapPago, String valor) {
		for (Entry<Character, String> entry : mapPago.entrySet()) {
			if (entry.getValue().equals(valor)) {
				return entry.getKey();
			}
		}
		return null;
	}

}
