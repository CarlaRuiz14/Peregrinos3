package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.CheckParada;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.services.ConjuntoServicioService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.Validaciones;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * ServicioController es el controlador encargado de gestionar la edición de
 * servicios y la asignación de paradas a cada servicio.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
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
	private Validaciones validaciones;

	@Autowired
	private LabelFeed label;

	private boolean checkNombre = true;
	private boolean checkPrecio = true;

	/**
	 * Inicializa el controlador.
	 * <ul>
	 * <li>Carga la tabla de servicios y establece un listener para actualizar la
	 * tabla de paradas en función del servicio seleccionado.</li>
	 * <li>Configura imágenes, atajos de teclado y tooltips para los botones.</li>
	 * </ul>
	 *
	 * @param location  La URL para resolver el objeto raíz, o null.
	 * @param resources Los recursos de localización, o null.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		cargarServiciosTabla();
		tblParadas.setPlaceholder(new Label("Selecciona un servicio"));

		tblServicios.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal == null) {
				tblParadas.setItems(FXCollections.emptyObservableList());
				tblParadas.setPlaceholder(new Label("Sin paradas"));
			} else {
				cargarParadasTabla(newVal);
			}
		});

		ayuda.configImgInfo(hpInfo);
		String rutaNuevo = resources.getString("btnNuevo.icon");
		Image imgNuevo = new Image(getClass().getResourceAsStream(rutaNuevo));
		btnNuevo.setGraphic(botones.createImageView(imgNuevo));
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

	/**
	 * Muestra la ayuda relacionada con la edición de servicios.
	 *
	 * @param event Evento de acción disparado al hacer clic en el enlace de ayuda.
	 * @throws IOException Si ocurre un error al cargar la ayuda.
	 */
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/servicios.html", stage);
	}

	/**
	 * Agrega un nuevo servicio a la tabla.
	 * <ul>
	 * <li>Crea un servicio vacío mediante el servicio de conjunto de
	 * servicios.</li>
	 * <li>Lo añade a la tabla de servicios, lo selecciona y lo desplaza a la
	 * vista.</li>
	 * <li>Inicia la edición de la celda de nombre del nuevo servicio.</li>
	 * </ul>
	 *
	 * @param event Evento de acción disparado al hacer clic en "Nuevo".
	 * @throws IOException Si ocurre un error durante la acción.
	 */
	@FXML
	private void handlerNuevo(ActionEvent event) throws IOException {

		Servicio nuevo = css.crearServicioVacio();

		tblServicios.getItems().add(nuevo);

		tblServicios.getSelectionModel().select(nuevo);
		tblServicios.scrollTo(nuevo);
		tblServicios.requestFocus();

		tblServicios.edit(tblServicios.getItems().size() - 1, colNombreServicio);
	}

	/**
	 * Guarda los cambios realizados en los servicios.
	 * <ul>
	 * <li>Valida que el nombre y el precio sean correctos.</li>
	 * <li>Solicita confirmación al usuario para guardar todos los cambios.</li>
	 * <li>Guarda cada servicio mediante el servicio de conjunto de servicios.</li>
	 * <li>Muestra mensajes informativos según el resultado.</li>
	 * </ul>
	 *
	 * @param event Evento de acción disparado al hacer clic en "Guardar".
	 * @throws IOException Si ocurre un error durante el guardado.
	 */
	@FXML
	private void handlerGuardar(ActionEvent event) throws IOException {

		try {
			if (checkNombre && checkPrecio) {
				boolean respuesta = alertas.alertaConfirmacion("Confirmación de Guardado",
						"Se guardarán todos los cambios realizados en los servicios.\n" + "¿Desea continuar?");
				if (respuesta) {
					for (Servicio s : tblServicios.getItems()) {
						List<Long> paradasActualizadas = new ArrayList<Long>(s.getListaParadas());
						s.setListaParadas(paradasActualizadas);
						css.saveServicio(s);
					}
					alertas.alertaInformacion("Guardado Exitoso", "Los cambios se han guardado correctamente.");
				} else {
					alertas.alertaInformacion("Operación Cancelada", "Los cambios no se guardarán.\nVolviendo...");
				}
				lblFeed.setText(" ");

			} else {
				alertas.alertaError("Error al guardar",
						"Hay problemas con los datos introducidos.\nPor favor, revise la infomación.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			alertas.alertaError("Error", "Hubo un problema al registrar los servicios.");
		}

	}

	/**
	 * Regresa a la vista de administrador.
	 *
	 * @param event Evento de acción disparado al hacer clic en "Volver".
	 * @throws IOException Si ocurre un error al cambiar de escena.
	 */
	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.ADMIN);
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

	/**
	 * Carga la tabla de servicios con los datos existentes.
	 */
	public void cargarServiciosTabla() {
		tblServicios.setEditable(true);
		Set<Servicio> listaS = css.findAllServicios();

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

			if (newValue == null || newValue.trim().isEmpty()) {
				servicio.setNombre(oldValue);
				tblServicios.refresh();
				label.mostrarTxtInvalido(lblFeed, "El nombre no puede estar vacío");
				checkNombre = false;
			} else if (css.existeNombreServicio(newValue) || existeNombreEnTabla(newValue, servicio)) {
				servicio.setNombre(oldValue);
				tblServicios.refresh();
				label.mostrarTxtInvalido(lblFeed, "El nombre '" + newValue + "' ya existe");
				checkNombre = false;
			} else if (!validaciones.validarNombreYApellidos(newValue)) {
				servicio.setNombre(oldValue);
				tblServicios.refresh();
				label.mostrarTxtInvalido(lblFeed, "Nombre sin números ni carac. especiales");
				checkNombre = false;
			} else {
				servicio.setNombre(newValue);
				label.mostrarTxtValido(lblFeed, "Nombre actualizado correctamente a '" + newValue);
				checkNombre = true;
			}
		});

		colPrecioServicio.setCellValueFactory(new PropertyValueFactory<>("precio"));
		colPrecioServicio.setCellFactory(TextFieldTableCell.forTableColumn(getConversorDouble()));
		colPrecioServicio.setOnEditCommit(event -> {
			Servicio servicio = event.getRowValue();
			Double oldValue = servicio.getPrecio();
			Double newValue = event.getNewValue();

			lblFeed.setText("");
			checkPrecio = true;

			if (newValue == null) {
				servicio.setPrecio(oldValue);
				tblServicios.refresh();
				label.mostrarTxtInvalido(lblFeed, "El precio debe ser un número válido");
				checkPrecio = false;
			} else {
				servicio.setPrecio(newValue);
				tblServicios.refresh();
				label.mostrarTxtValido(lblFeed, "Precio actualizado correctamente");
				checkPrecio = true;
			}
		});
	}

	/**
	 * Verifica si un nombre ya existe en la tabla, excluyendo el servicio actual.
	 *
	 * @param nombre         El nombre a verificar.
	 * @param servicioActual El servicio que se está editando.
	 * @return true si el nombre existe en otro servicio, false en caso contrario.
	 */
	private boolean existeNombreEnTabla(String nombre, Servicio servicioActual) {
		for (Servicio s : tblServicios.getItems()) {
			if (s != servicioActual && s.getNombre().equalsIgnoreCase(nombre)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Carga la tabla de paradas asociadas al servicio seleccionado.
	 * <ul>
	 * <li>Recupera la lista de todas las paradas.</li>
	 * <li>Crea una lista de objetos CheckParada que indican si cada parada está
	 * asociada al servicio.</li>
	 * <li>Configura las columnas de la tabla de paradas: ID, nombre, región y el
	 * check de asociación.</li>
	 * <li>Agrega listeners para actualizar la lista de paradas del servicio al
	 * marcar o desmarcar.</li>
	 * </ul>
	 *
	 * @param servicioSeleccionado El servicio para el cual se cargarán las paradas.
	 */
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

	/**
	 * Crea un conversor para valores de tipo Double, formateándolos con dos
	 * decimales.
	 *
	 * @return Un StringConverter para Double.
	 */
	private StringConverter<Double> getConversorDouble() {
		return new StringConverter<Double>() {

			private final DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.US);
			private final DecimalFormat df;
			{
				df = new DecimalFormat("#0.00", symbols);
				df.setRoundingMode(RoundingMode.HALF_UP);
			}

			@Override
			public String toString(Double value) {
				if (value == null) {
					return "";
				}
				return df.format(value);
			}

			@Override
			public Double fromString(String text) {
				if (text == null || text.trim().isEmpty()) {
					return null;
				}
				try {
					text = text.replace(',', '.');

					Number number = df.parse(text);

					return number.doubleValue();
				} catch (Exception e) {
					return null;
				}
			}
		};
	}

}
