package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.DatosSellado;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.NacionalidadService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.ParadasPeregrinoService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * SellarController es el controlador para la vista de sellado de carnet.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Controller
public class SellarController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private Label lblIdParada;

	@FXML
	private Label lblNombre;

	@FXML
	private Label lblRegion;

	@FXML
	private TableView<Peregrino> tblPeregrinos = new TableView<>();

	@FXML
	private TableColumn<Peregrino, Long> colId;

	@FXML
	private TableColumn<Peregrino, String> colNombre;

	@FXML
	private TableColumn<Peregrino, String> colNacionalidad;

	@FXML
	private TableColumn<Peregrino, Long> colIdCarnet;

	@FXML
	private Button btnSellar;

	@FXML
	private Button btnVolver;

	@FXML
	private Button btnSalir;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private Sesion sesion;

	@Autowired
	private Alertas alertas;

	@Autowired
	private Botones botones;

	@Autowired
	private Ayuda ayuda;

	@Autowired
	private DatosSellado datosSellado;

	@Autowired
	private PeregrinoService peregrinoService;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private ParadasPeregrinoService paradasPeregrinoService;

	@Autowired
	private NacionalidadService nacionalidadService;

	@Autowired
	private Mnemonic mnemonicConfig;

	@Autowired
	private Tooltips tooltipConfig;

	private Parada parada;
	private Carnet carnet;

	/**
	 * Inicializa la vista del controlador SellarController.
	 * <ul>
	 * <li>Obtiene la parada asociada al usuario activo y la asigna a
	 * datosSellado.</li>
	 * <li>Muestra los datos de la parada (ID, nombre y región) en los labels
	 * correspondientes.</li>
	 * <li>Configura la tabla de peregrinos, asignando las columnas y estableciendo
	 * el contenido.</li>
	 * <li>Configura atajos de teclado y tooltips para los botones de sellar, volver
	 * y salir.</li>
	 * </ul>
	 *
	 * @param location  La URL utilizada para resolver el objeto raíz, o null.
	 * @param resources Los recursos de localización, o null.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		parada = paradaService.findByUsuario(sesion.getUsuarioActivo().getId());
		datosSellado.setParada(parada);

		lblIdParada.setText(String.valueOf(parada.getId()));
		lblNombre.setText(parada.getNombre());
		lblRegion.setText(String.valueOf(parada.getRegion()));

		ayuda.configImgInfo(hpInfo);

		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
		colIdCarnet.setCellValueFactory(cellData -> {
			carnet = cellData.getValue().getCarnet();
			return new SimpleObjectProperty<>(carnet != null ? carnet.getId() : null);
		});

		ObservableList<Peregrino> listPeregrinos = FXCollections.observableArrayList(peregrinoService.findAll());

		tblPeregrinos.setItems(listPeregrinos);
		tblPeregrinos.setPlaceholder(new Label("Sin peregrinos registrados"));

		botones.imgFlecha(btnSellar);
		botones.imgVolver(btnVolver);
		botones.imgSalir(btnSalir);

		mnemonicConfig.infoMnemonic(hpInfo);
		btnSellar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
				btnSellar.fire();
				event.consume();
			}
		});
		mnemonicConfig.volverMnemonic(btnVolver);
		mnemonicConfig.salirMnemonic(btnSalir);

		tooltipConfig.salirTooltip(btnSalir);
		btnSellar.setTooltip(new Tooltip("Sellar (Alt+Enter)"));
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);
	}

	/**
	 * Muestra la ayuda relacionada con el sellado del carnet.
	 * 
	 * @param event Evento de acción disparado al hacer clic en el enlace de ayuda.
	 * @throws IOException Si ocurre un error al cargar el recurso de ayuda.
	 */
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/sellar.html", stage);
	}

	/**
	 * Procesa el sellado del carnet para el peregrino seleccionado.
	 * <ul>
	 * <li>Verifica que se haya seleccionado un peregrino en la tabla.</li>
	 * <li>Comprueba que el peregrino no tenga ya registrada la parada para
	 * hoy.</li>
	 * <li>Asigna el peregrino y el carnet a datosSellado.</li>
	 * <li>Muestra un mensaje de confirmación con los datos del peregrino y la
	 * parada.</li>
	 * <li>Si se confirma, registra la parada y sella el carnet, notificando al
	 * usuario del éxito.</li>
	 * </ul>
	 *
	 * @param event Evento de acción disparado al hacer clic en "Sellar".
	 * @throws IOException Si ocurre un error durante el proceso de sellado.
	 */
	@FXML
	private void handlerSellar(ActionEvent event) throws IOException {

		try {
			Peregrino peregrinoSeleccionado = tblPeregrinos.getSelectionModel().getSelectedItem();
			if (peregrinoSeleccionado == null) {
				alertas.alertaError("Error", "Debe seleccionar un peregrino para sellar su carnet.");
				return;
			}

			if (paradasPeregrinoService.existeParada(parada, peregrinoSeleccionado)) {
				alertas.alertaError("Error", "El peregrino " + peregrinoSeleccionado.getNombre()
						+ " ya tiene registrada la parada " + parada.getNombre() + " para hoy.");
				return;
			}

			datosSellado.setPeregrino(peregrinoSeleccionado);
			carnet = peregrinoSeleccionado.getCarnet();
			datosSellado.setCarnet(carnet);

			String mensaje = "Se va a sellar el carnet del peregrino: \n\tID: " + peregrinoSeleccionado.getId()
					+ "\n\tPeregrino: " + peregrinoSeleccionado.getNombre() + "\n\tNacionalidad: "
					+ nacionalidadService.mapaNacionalidades().get(peregrinoSeleccionado.getNacionalidad())
					+ "\n\tID Carnet: " + carnet.getId() + "\nEn la parada:\n\tID Parada: " + parada.getId()
					+ "\n\tNombre: " + parada.getNombre() + "\n\tRegión: " + parada.getRegion();

			boolean confirmar = alertas.alertaConfirmacion("Confirmar datos", mensaje);

			if (confirmar) {
				paradasPeregrinoService.registrarParadaYSellarCarnet(carnet, parada, peregrinoSeleccionado);
				String mensajeExito = "El carnet del peregrino " + peregrinoSeleccionado.getNombre()
						+ " ha sido sellado correctamente.";
				alertas.alertaInformacion("Sellado exitoso", mensajeExito);

				stageManager.switchScene(FxmlView.ALOJAR);
			} else {
				alertas.alertaInformacion("Acción cancelada",
						"Por favor, seleccione un peregrino \npara sellar su carnet.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			alertas.alertaError("Error de sellado",
					"Hubo un error al intentar sellar el carnet del peregrino, revise la infomación. ");
			return;
		}
	}

	/**
	 * Regresa a la vista de la parada.
	 * 
	 * @param event Evento de acción disparado al hacer clic en "Volver".
	 * @throws IOException Si ocurre un error al cambiar de escena.
	 */
	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.PARADA);
	}

	/**
	 * Ejecuta la acción de salida de la aplicación.
	 * 
	 * @param event Evento de acción disparado al hacer clic en "Salir".
	 * @throws IOException Si ocurre un error durante la salida.
	 */
	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}
}
