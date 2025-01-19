package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.AyudaConfig;
import com.luisdbb.tarea3AD2024base.config.BotonesConfig;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Controller
public class CarnetController implements Initializable {

	// falta corregir columna region
	// cuando se pongan los datos en las tablas extraerlos a la service

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private ImageView imgCarnet;

	@FXML
	private Label lblIdCarnet;

	@FXML
	private Label lblExpedicion;

	@FXML
	private Label lblIdPeregrino;

	@FXML
	private Label lblNombre;

	@FXML
	private Label lblApellidos;

	@FXML
	private Label lblNacionalidad;

	@FXML
	private Label lblDistancia;

	@FXML
	private Label lblVips;

	@FXML
	private TableView<Parada> tblParadas = new TableView<>();

	@FXML
	private TableColumn<Parada, Long> colIdParada;

	@FXML
	private TableColumn<Parada, String> colNombreParada;

	@FXML
	private TableColumn<Parada, Character> colRegion;

	@FXML
	private TableView<Estancia> tblEstancias = new TableView<>();

	@FXML
	private TableColumn<Estancia, Long> colIdEstancia;

	@FXML
	private TableColumn<Estancia, LocalDate> colFecha;

	@FXML
	private TableColumn<Estancia, Boolean> colVip;

	@FXML
	private Button btnExportar;

	@FXML
	private Button btnVolver;

	@FXML
	private Button btnSalir;

	// inyecciones
	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private BotonesConfig botones;
	
	@Autowired
	private AyudaConfig ayuda;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// config info
		ayuda.configImgInfo(hpInfo);

		// config img peregrino
		String rutaPer = resources.getString("carnet.icon");
		imgCarnet.setImage(new Image(getClass().getResourceAsStream(rutaPer)));

		// config de la tabla Paradas
		colIdParada.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombreParada.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));

		ObservableList<Parada> listParadas = FXCollections.observableArrayList(new Parada(1, "Pruebaa", 'P'),
				new Parada(1, "Pruebaa", 'P'), new Parada(1, "Pruebaa", 'P'), new Parada(1, "Pruebaa", 'P'),
				new Parada(1, "Pruebaa", 'P'), new Parada(1, "Pruebaa", 'P'));

		tblParadas.setItems(listParadas);

		// config tabla Estancias
		colIdEstancia.setCellValueFactory(new PropertyValueFactory<>("id"));
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
		// prueba:
		ObservableList<Estancia> listEstancias = FXCollections.observableArrayList(
				new Estancia(1, LocalDate.of(1990, 9, 14), true), new Estancia(1, LocalDate.of(1990, 9, 14), true),
				new Estancia(1, LocalDate.of(1990, 9, 14), true));

		tblEstancias.setItems(listEstancias);

		// config img btn Exportar
		botones.configImgFlecha(btnExportar);

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
		ayuda.configInfo("/help/help.html");
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
		stageManager.switchScene(FxmlView.PEREGRINO);
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
