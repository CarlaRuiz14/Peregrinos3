package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.Alertas;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
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
	private PeregrinoService peregrinoService;

	@Autowired
	private ParadaService paradaService;

	Parada parada;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		parada = paradaService.findById(sesion.getUsuarioActivo().getId());

		// cargar datos parada
		lblIdParada.setText(String.valueOf(parada.getId()));
		lblNombre.setText(parada.getNombre());
		lblRegion.setText(String.valueOf(parada.getRegion()));

		// config info
		String rutaInfo = resources.getString("info.icon");
		Image imagen = new Image(getClass().getResourceAsStream(rutaInfo));
		ImageView imageView = new ImageView(imagen);
		imageView.setFitWidth(30);
		imageView.setFitHeight(30);
		imageView.setPreserveRatio(true);
		hpInfo.setGraphic(imageView);

		// config tabla
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colNacionalidad.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
		colIdCarnet.setCellValueFactory(cellData -> {
			// Obtenemos el carnet asociado al peregrino
			Carnet carnet = cellData.getValue().getCarnet();
			// Retornamos el ID del carnet como una propiedad observable de tipo Long
			return new SimpleObjectProperty<>(carnet != null ? carnet.getId() : null);
		});

		ObservableList<Peregrino> listPeregrinos = FXCollections.observableArrayList(peregrinoService.findAll());

		tblPeregrinos.setItems(listPeregrinos);

		// config img btn Sellar
		String rutaSellar = resources.getString("btnSellar.icon");
		Image imgSellar = new Image(getClass().getResourceAsStream(rutaSellar));
		ImageView viewSellar = new ImageView(imgSellar);
		viewSellar.setFitWidth(60);
		viewSellar.setFitHeight(30);
		btnSellar.setGraphic(viewSellar);

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
	private void handlerSellar(ActionEvent event) throws IOException {

		Peregrino peregrinoSeleccionado = tblPeregrinos.getSelectionModel().getSelectedItem();

		if (peregrinoSeleccionado == null) {
			Alertas.alertaError("Error", "Debe seleccionar un peregrino para sellar su carnet.");
			return;
		}

		String mensaje = "Se va a sellar el carnet del peregrino: \n\tID: "+peregrinoSeleccionado.getId()+"\n\tPeregrino: " + peregrinoSeleccionado.getNombre()
				+ "\n\tNacionalidad: " + peregrinoSeleccionado.getNacionalidad() + "\n\tID Carnet: "
				+ peregrinoSeleccionado.getCarnet().getId() + "\nEn la parada:\n\tID Parada: " + parada.getNombre()
				+ "\n\tNombre: " + parada.getNombre() + "\n\tRegión: " + parada.getRegion();

		boolean confirmar = Alertas.alertaConfirmacion("Confirmar datos", mensaje);

		if (confirmar) {
			stageManager.switchScene(FxmlView.ALOJAR);
		} else {
			Alertas.alertaInformacion("Acción cancelada",
					"Por favor, seleccione un peregrino \npara sellar su carnet.");
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
