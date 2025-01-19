package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.Alertas;
import com.luisdbb.tarea3AD2024base.config.AyudaConfig;
import com.luisdbb.tarea3AD2024base.config.BotonesConfig;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.DatosSellado;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.ParadasPeregrinoService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
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

/**
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

	// inyecciones
	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private Sesion sesion;

	@Autowired
	private Alertas alertas;

	@Autowired
	private BotonesConfig botones;

	@Autowired
	private AyudaConfig ayuda;

	@Autowired
	private DatosSellado datosSellado;

	@Autowired
	private PeregrinoService peregrinoService;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private ParadasPeregrinoService paradasPeregrinoService;

	Parada parada;
	Carnet carnet;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		parada = paradaService.findByUsuario(sesion.getUsuarioActivo().getId());
		datosSellado.setParada(parada);

		// cargar datos parada
		lblIdParada.setText(String.valueOf(parada.getId()));
		lblNombre.setText(parada.getNombre());
		lblRegion.setText(String.valueOf(parada.getRegion()));

		// config info
		ayuda.configImgInfo(hpInfo);

		// config tabla
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
		colIdCarnet.setCellValueFactory(cellData -> {
			// Obtenemos el carnet asociado al peregrino
			carnet = cellData.getValue().getCarnet();
			// Retornamos el ID del carnet como una propiedad observable de tipo Long
			return new SimpleObjectProperty<>(carnet != null ? carnet.getId() : null);
		});

		ObservableList<Peregrino> listPeregrinos = FXCollections.observableArrayList(peregrinoService.findAll());

		tblPeregrinos.setItems(listPeregrinos);

		// config img btn Sellar
		botones.configImgFlecha(btnSellar);

		// config img btn Volver
		botones.configImgVolver(btnVolver);

		// config img btn Salir
		botones.configImgSalir(btnSalir);

		// mnemónicos
		hpInfo.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.I) {
				hpInfo.fire();
				event.consume();
			}
		});

		btnSellar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.X) {
				btnSellar.fire();
				event.consume();
			}
		});

		btnVolver.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.V) {
				btnVolver.fire();
				event.consume();
			}
		});

		btnSalir.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.S) {
				btnSalir.fire();
				event.consume();
			}
		});

		// tooltips
		hpInfo.setTooltip(new Tooltip("Info (Alt+I)"));
		btnSellar.setTooltip(new Tooltip("Sellar (Alt+X)"));
		btnVolver.setTooltip(new Tooltip("Volver (Alt+V)"));
		btnSalir.setTooltip(new Tooltip("Salir (Alt+S)"));

	}

	/**
	 * Handler para el HyperLink hpInfo. Método que muestra el sistema de ayuda al
	 * pulsarlo.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		ayuda.configInfo("/help/help.html");
	}

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

			String mensaje = "Se va a sellar el carnet del peregrino: \n\tID: " + peregrinoSeleccionado.getId()
					+ "\n\tPeregrino: " + peregrinoSeleccionado.getNombre() + "\n\tNacionalidad: "
					+ peregrinoSeleccionado.getNacionalidad() + "\n\tID Carnet: " + carnet.getId()
					+ "\nEn la parada:\n\tID Parada: " + parada.getId() + "\n\tNombre: " + parada.getNombre()
					+ "\n\tRegión: " + parada.getRegion();

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
			System.out.println(e.getMessage());
			e.printStackTrace();
			alertas.alertaError("Error de sellado",
					"Hubo un error al intentar sellar el carnet del peregrino, revise la infomación. ");
		}
	}

	/**
	 * Handler para el botón btnVolver. Método que al pulsarlo vuelve a la ventana
	 * de Login.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.PARADA);
	}

	/**
	 * Handler para botón btnSalir. Método que sale de la aplicación al pulsarlo.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		Platform.exit();
	}

}
