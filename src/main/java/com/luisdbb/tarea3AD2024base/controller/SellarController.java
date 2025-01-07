package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

	// mirar
	@Autowired
	private UsuarioService userService;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// configuracion info
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

		Carnet c1 = new Carnet(1);

		// prueba:
		ObservableList<Peregrino> listPeregrinos = FXCollections.observableArrayList(
				new Peregrino(1, "Maria", "Española", c1), new Peregrino(1, "Maria", "Española", c1),
				new Peregrino(1, "Maria", "Española", c1), new Peregrino(1, "Maria", "Española", c1),
				new Peregrino(1, "Maria", "Española", c1), new Peregrino(1, "Maria", "Española", c1),
				new Peregrino(1, "Maria", "Española", c1));

		tblPeregrinos.setItems(listPeregrinos);

		// configuracion imagen boton Sellar
		String rutaSellar = resources.getString("btnSellar.icon");
		Image imgSellar = new Image(getClass().getResourceAsStream(rutaSellar));
		ImageView viewSellar = new ImageView(imgSellar);
		viewSellar.setFitWidth(60);
		viewSellar.setFitHeight(30);
		btnSellar.setGraphic(viewSellar);

		// configuracion imagen boton Volver
		String rutaVolver = resources.getString("btnVolver.icon");
		Image imgVolver = new Image(getClass().getResourceAsStream(rutaVolver));
		ImageView viewVolver = new ImageView(imgVolver);
		viewVolver.setFitWidth(20);
		viewVolver.setFitHeight(20);
		btnVolver.setGraphic(viewVolver);

		// configuracion imagen boton Salir
		String rutaSalir = resources.getString("btnSalir.icon");
		Image imgSalir = new Image(getClass().getResourceAsStream(rutaSalir));
		ImageView viewSalir = new ImageView(imgSalir);
		viewSalir.setFitWidth(20);
		viewSalir.setFitHeight(20);
		btnSalir.setGraphic(viewSalir);

		// mnenomicos
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

	// handler botones
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {

	}

	@FXML
	private void handlerSellar(ActionEvent event) throws IOException {

	}

	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.MAIN);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		Platform.exit();
	}

}
