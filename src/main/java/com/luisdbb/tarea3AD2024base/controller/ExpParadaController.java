package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.Alertas;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Controller
public class ExpParadaController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private Label lblIdPar;

	@FXML
	private Label lblNombrePar;

	@FXML
	private Label lblRegionPar;

	@FXML
	private Label lblIdResp;

	@FXML
	private Label lblUsuario;

	@FXML
	private Label lblEmail;

	@FXML
	private DatePicker dateFechaI;

	@FXML
	private DatePicker dateFechaF;

	@FXML
	private Button btnBuscar;

	@FXML
	private TableView<Estancia> tblEstancias;

	@FXML
	private TableColumn<Estancia, Long> colIdEstancia;

	@FXML
	private TableColumn<Estancia, String> colPeregrino;

	@FXML
	private TableColumn<Estancia, LocalDate> colFecha;

	@FXML
	private TableColumn<Estancia, Boolean> colVip;

	@FXML
	private Button btnExportar;

	@FXML
	private Button btnVolver;

	@FXML
	private Label lblFeed;

	@FXML
	private Button btnSalir;

	// inyecciones
	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private Sesion sesion;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private EstanciaService estanciaService;

	Parada parada;
	LocalDate fechaInicio;
	LocalDate fechaFin;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		parada = paradaService.findByUsuario(sesion.getUsuarioActivo().getId());
	

		// cargar datos parada
		lblIdPar.setText(String.valueOf(parada.getId()));
		lblNombrePar.setText(parada.getNombre());
		lblRegionPar.setText(String.valueOf(parada.getRegion()));
		lblIdResp.setText(String.valueOf(sesion.getUsuarioActivo().getId()));
		lblUsuario.setText(sesion.getUsuarioActivo().getNombreUsuario());
		lblEmail.setText(sesion.getUsuarioActivo().getEmail());

		// config info
		String rutaInfo = resources.getString("info.icon");
		Image imagen = new Image(getClass().getResourceAsStream(rutaInfo));
		ImageView imageView = new ImageView(imagen);
		imageView.setFitWidth(30);
		imageView.setFitHeight(30);
		imageView.setPreserveRatio(true);
		hpInfo.setGraphic(imageView);

		// config tabla estancias
		colIdEstancia.setCellValueFactory(new PropertyValueFactory<>("id"));
		colPeregrino.setCellValueFactory(cellData -> {
			// Obtenemos el peregrino asociado a la estancia
			Peregrino peregrino = cellData.getValue().getPeregrino();
			// Retornamos el nombre como una propiedad observable
			return new SimpleStringProperty(peregrino != null ? peregrino.getNombre() : "");
		});
		colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		colVip.setCellValueFactory(new PropertyValueFactory<>("vip"));

		colVip.setCellFactory(tc -> new TableCell<Estancia, Boolean>() {
			@Override
			protected void updateItem(Boolean vip, boolean empty) {
				super.updateItem(vip, empty);
				if (empty || vip == null) {
					setText(null);
				} else {
					setText(vip ? "Sí" : "No");
				}
			}
		});

		// config img btn Buscar
		String rutaBuscar = resources.getString("btnBuscar.icon");
		Image imgBuscar = new Image(getClass().getResourceAsStream(rutaBuscar));
		ImageView viewBuscar = new ImageView(imgBuscar);
		viewBuscar.setFitWidth(25);
		viewBuscar.setFitHeight(25);
		btnBuscar.setGraphic(viewBuscar);

		// config img btn Exportar
		String rutaExportar = resources.getString("btnExportar.icon");
		Image imgExp = new Image(getClass().getResourceAsStream(rutaExportar));
		ImageView viewExp = new ImageView(imgExp);
		viewExp.setFitWidth(60);
		viewExp.setFitHeight(30);
		btnExportar.setGraphic(viewExp);

		// config img btn Volver
		String rutaVolver = resources.getString("btnVolver.icon");
		Image imgVolver = new Image(getClass().getResourceAsStream(rutaVolver));
		ImageView viewVolver = new ImageView(imgVolver);
		viewVolver.setFitWidth(20);
		viewVolver.setFitHeight(20);
		btnVolver.setGraphic(viewVolver);

		// config img btn Salir
		String rutaSalir = resources.getString("btnSalir.icon");
		Image imgSalir = new Image(getClass().getResourceAsStream(rutaSalir));
		ImageView viewSalir = new ImageView(imgSalir);
		viewSalir.setFitWidth(20);
		viewSalir.setFitHeight(20);
		btnSalir.setGraphic(viewSalir);

		// mnemónicos
		hpInfo.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.I) {
				hpInfo.fire();
				event.consume();
			}
		});

		btnBuscar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.B) {
				btnBuscar.fire();
				event.consume();
			}
		});

		btnExportar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.X) {
				btnExportar.fire();
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
		btnBuscar.setTooltip(new Tooltip("Buscar (Alt+B)"));
		btnExportar.setTooltip(new Tooltip("Exportar (Alt+X)"));
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
		WebView webView = new WebView();

		String url = getClass().getResource("/help/help.html").toExternalForm();
		webView.getEngine().load(url);

		Stage helpStage = new Stage();
		helpStage.setTitle("Info");

		Scene helpScene = new Scene(webView, 600, 600);
		helpStage.setScene(helpScene);

		// Bloquear la ventana principal mientras se muestra la ayuda
		helpStage.initModality(Modality.APPLICATION_MODAL);
		helpStage.setResizable(false);

		helpStage.show();
	}

	@FXML
	private void handlerBuscar(ActionEvent event) throws IOException {

		if (!validarFechas()) {
			return;
		}

		List<Estancia> estancias = estanciaService.findByIdParadaAndFechaBetween(parada.getId(), fechaInicio, fechaFin);

		ObservableList<Estancia> listEstancias = FXCollections.observableArrayList(estancias);

		tblEstancias.setItems(listEstancias);

	}

	private boolean validarFechas() {
		LocalDate hoy = LocalDate.now();
		fechaInicio = dateFechaI.getValue();
		fechaFin = dateFechaF.getValue();
		
		 // Depuración
	    System.out.println("Fecha de inicio: " + fechaInicio);
	    System.out.println("Fecha de fin: " + fechaFin);
		

		if (fechaInicio == null || fechaFin == null) {
			Alertas.alertaError("Error", "Debes seleccionar ambas fechas.");
			return false;
		}

		if (fechaInicio.isAfter(hoy)) {
			Alertas.alertaError("Error", "La fecha de inicio debe ser anterior al día de hoy.");
			return false;
		}

		if (fechaFin.isAfter(hoy)) {
			Alertas.alertaError("Error", "La fecha de fin debe ser anterior al día de hoy.");
			return false;
		}

		if (fechaInicio.isAfter(fechaFin)) {
			Alertas.alertaError("Error", "La fecha de inicio debe ser anterior a la fecha de fin.");
			return false;
		}

		return true;
	}

	@FXML
	private void handlerExportar(ActionEvent event) throws IOException {

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
