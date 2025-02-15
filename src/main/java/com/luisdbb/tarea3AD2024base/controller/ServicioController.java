package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.CheckParada;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.services.ConjuntoServicioService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

@Component
public class ServicioController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private TableView<Servicio> tblServicios = new TableView<>();

	@FXML
	private TableColumn<Servicio, Long> colIdServicio;

	@FXML
	private TableColumn<Servicio, String> colNombreServicio;

	@FXML
	private TableColumn<Servicio, Double> colPrecioServicio;

	@FXML
	private TableView<CheckParada> tblParadas = new TableView<>();

	@FXML
	private TableColumn<CheckParada, Long> colIdParada;

	@FXML
	private TableColumn<CheckParada, String> colNombreParada;

	@FXML
	private TableColumn<CheckParada, Character> colRegionParada;

	@FXML
	private TableColumn<CheckParada, Boolean> colCheckParada;

	@FXML
	private Button btnNuevo;

	@FXML
	private Button btnGuardar;

	@FXML
	private Button btnVolver;

	@FXML
	private Label lblFeed;

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

	@Autowired
	private ConjuntoServicioService css;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private Alertas alertas;

	@Autowired
	private LabelFeed label;

	private boolean checkNombre = true;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		cargarServiciosTabla();
		tblParadas.setPlaceholder(new Label("Selecciona un servicio"));


		// Servicio Seleccionado
		tblServicios.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal == null) {
				tblParadas.setItems(FXCollections.emptyObservableList());
				tblParadas.setPlaceholder(new Label("Sin paradas"));
			} else {

				cargarParadasTabla(newVal);
			}
		});

		// Botones, mnemonicos y tooltips
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

	public void cargarServiciosTabla() {
		tblServicios.setEditable(true);
		List<Servicio> listaS = css.findAllServicios();

		ObservableList<Servicio> listaServicios = FXCollections.observableArrayList(listaS);
		tblServicios.setItems(listaServicios);

		colIdServicio.setCellValueFactory(new PropertyValueFactory<>("id"));

		colNombreServicio.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colNombreServicio.setCellFactory(TextFieldTableCell.forTableColumn());
		colNombreServicio.setOnEditCommit(event -> {
			Servicio servicio = event.getRowValue();
			String oldValue = servicio.getNombre();
			String newValue = event.getNewValue();
			lblFeed.setText(" ");
			checkNombre = true;

			if (css.existeNombreServicio(newValue) || existeNombreEnTabla(newValue, servicio)) {

				servicio.setNombre(oldValue);

				tblServicios.refresh();
				label.mostrarTxtInvalido(lblFeed, "El nombre '" + newValue + "' ya existe");
				checkNombre = false;
			} else {
				servicio.setNombre(newValue);
				label.mostrarTxtValido(lblFeed, "Nombre actualizado correctamente a '" + newValue + "'.");
				checkNombre = true;
			}
		});

		colPrecioServicio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		colPrecioServicio.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
		colPrecioServicio.setOnEditCommit(event -> {
			Servicio servicio = event.getRowValue();
			servicio.setPrecio(event.getNewValue());
		});
	}

	private boolean existeNombreEnTabla(String nombre, Servicio servicioActual) {
		for (Servicio s : tblServicios.getItems()) {
			if (s != servicioActual && s.getNombre().equalsIgnoreCase(nombre)) {
				return true;
			}
		}
		return false;
	}

	public void cargarParadasTabla(Servicio servicioSeleccionado) {
		tblParadas.setEditable(true);
		colCheckParada.setEditable(true);

		// Lista con todas las paradas existentes
		List<Parada> todasParadas = paradaService.findAll();

		// Lista de paradas con servicio
		List<Long> paradasServicio = servicioSeleccionado.getListaParadas();

		// Lista de CheckParada con Parada y check
		List<CheckParada> listaCheck = new ArrayList<>();

		for (Parada pTodas : todasParadas) {
			boolean contiene = paradasServicio.contains(pTodas.getId());
			CheckParada cp = new CheckParada(pTodas, contiene);
			listaCheck.add(cp);
		}

		for (CheckParada cp : listaCheck) {
			cp.checkProperty().addListener((obs, oldVal, newVal) -> {
				if (newVal) {
					servicioSeleccionado.getListaParadas().add(cp.getParada().getId());
				} else {
					servicioSeleccionado.getListaParadas().remove(cp.getParada().getId());
				}
			});
		}

		// Observable con valores de listaCheck
		ObservableList<CheckParada> listaTabla = FXCollections.observableArrayList(listaCheck);
		tblParadas.setItems(listaTabla);

		// columnas checkParada
		colIdParada.setCellValueFactory(
				cellData -> new SimpleObjectProperty<Long>(cellData.getValue().getParada().getId()));

		colNombreParada
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getParada().getNombre()));

		colRegionParada.setCellValueFactory(
				cellData -> new SimpleObjectProperty<Character>(cellData.getValue().getParada().getRegion()));

		colCheckParada.setCellValueFactory(cellData -> cellData.getValue().checkProperty());
		colCheckParada.setCellFactory(CheckBoxTableCell.forTableColumn(colCheckParada));

	}

	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/servicios.html", stage);
	}

	@FXML
	private void handlerNuevo(ActionEvent event) throws IOException {

		Servicio nuevo = css.crearServicioVacio();

		tblServicios.getItems().add(nuevo);

		tblServicios.getSelectionModel().select(nuevo);
		tblServicios.scrollTo(nuevo);

		tblServicios.edit(tblServicios.getItems().size() - 1, colNombreServicio);
	}

	@FXML
	private void handlerGuardar(ActionEvent event) throws IOException {

		if (checkNombre) {
			boolean respuesta = alertas.alertaConfirmacion("Confirmación de Guardado",
					"Se guardarán todos los cambios realizados en los servicios.\n" + "¿Desea continuar?");
			if (respuesta) {
				for (Servicio s : tblServicios.getItems()) {
					css.saveServicio(s);
				}
				alertas.alertaInformacion("Guardado Exitoso", "Los cambios se han guardado correctamente.");
			} else {
				alertas.alertaInformacion("Operación Cancelada", "Los cambios no se guardarán.\nVolviendo...");
			}
			lblFeed.setText(" ");

		} else {
			alertas.alertaError("Nombre Duplicado", "Existen nombres de servicio repetidos.\n"
					+ "Por favor revise los servicios con nombres duplicados y corríjalos antes de guardar.");
		}

	}

	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.ADMIN);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}
}
