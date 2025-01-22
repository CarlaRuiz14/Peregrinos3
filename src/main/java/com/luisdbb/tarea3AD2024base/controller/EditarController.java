package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.Alertas;
import com.luisdbb.tarea3AD2024base.config.AyudaConfig;
import com.luisdbb.tarea3AD2024base.config.BotonesConfig;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.config.Validaciones;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.NacionalidadService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */

@Controller
public class EditarController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private Label lblTitulo;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtApellidos;

	@FXML
	private TextField txtEmail;

	private final StringProperty emailProperty = new SimpleStringProperty();

	@FXML
	private ComboBox<String> cmbNacionalidad;

	ObservableList<String> listaNac;

	@FXML
	private Button btnLimpiar;

	@FXML
	private Button btnEditar;

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
	private Alertas alertas;

	@Autowired
	private Validaciones validaciones;

	@Autowired
	private AyudaConfig ayuda;

	@Autowired
	private NacionalidadService nacionalidadService;

	@Autowired
	private Sesion sesion;

	@Autowired
	private PeregrinoService peregrinoService;

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private BotonesConfig botones;

	Usuario usuarioActivo;
	Peregrino peregrinoActivo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// entidades a editar
		usuarioActivo = sesion.getUsuarioActivo();
		peregrinoActivo = peregrinoService.findByUsuario(usuarioActivo.getId());

		// cargar datos
		txtNombre.setText(peregrinoActivo.getNombre());
		txtApellidos.setText(peregrinoActivo.getApellidos());
		txtEmail.setText(usuarioActivo.getEmail());
		cmbNacionalidad.getSelectionModel().select(peregrinoActivo.getNacionalidad());

		validarEntradas();

		// combobox nacinalidades
		List<String> listaValores = new ArrayList<>(nacionalidadService.mapaNacionalidades().values());
		listaNac = FXCollections.observableArrayList(listaValores);
		cmbNacionalidad.setItems(listaNac);

		// config info
		ayuda.configImgInfo(hpInfo);

		// config img btn Limpiar
		botones.configImgLimpiar(btnLimpiar);

		// config img btn Editar
		botones.configImgFlecha(btnEditar);

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

		btnLimpiar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.L) {
				btnLimpiar.fire();
				event.consume();
			}
		});

		btnEditar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.E) {
				btnEditar.fire(); // Simula el clic en el botón
				event.consume(); // Detiene la propagación del evento
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
		btnLimpiar.setTooltip(new Tooltip("Limpiar (Alt+L)"));
		btnEditar.setTooltip(new Tooltip("Editar (Alt+E)"));
		btnVolver.setTooltip(new Tooltip("Volver (Alt+V)"));
		btnSalir.setTooltip(new Tooltip("Salir (Alt+S)"));

	}
	
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		ayuda.configInfo("/help/help.html");
	}

	@FXML
	private void handlerLimpiar(ActionEvent event) throws IOException {

		boolean confirmar = alertas.alertaConfirmacion("Borrado de formulario",
				"Se borrarán los datos introducidos, ¿está de acuerdo?");

		if (confirmar) {
			txtNombre.clear();
			txtApellidos.clear();
			txtEmail.clear();
			cmbNacionalidad.getSelectionModel().clearSelection();

		} else {
			alertas.alertaInformacion("Acción cancelada", "Por favor, rellene el formulario.");
		}
	}

	@FXML
	private void handlerEditar(ActionEvent event) throws IOException {

		if (!validarRegistro()) {
			return;
		}

		peregrinoActivo.setNombre(txtNombre.getText());
		peregrinoActivo.setApellidos(txtApellidos.getText());		
		peregrinoActivo.setNacionalidad(nacionalidadService.obtenerNacionalidadSeleccionada(cmbNacionalidad.getValue()));
		peregrinoService.actualizarDatosPeregrino(peregrinoActivo);
		
		usuarioActivo.setEmail(txtEmail.getText());
		usuarioService.actualizarDatosUsuario(usuarioActivo);		
		
		alertas.alertaInformacion("Actualización exitosa",
				"Los datos han sido guardados correctamente.\n\nVolviendo al menú.");
		stageManager.switchScene(FxmlView.PEREGRINO);
	}

	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.PEREGRINO);
	}
	
	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		Platform.exit();
	}

	private void validarEntradas() {
		lblFeed.setText(" ");

		emailProperty.bind(txtEmail.textProperty());

		emailProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!validaciones.validarEspacios(newValue)) {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedInvalido");
					lblFeed.setText("Email sin espacios en blanco");
				} else if (!validaciones.validarEmail(newValue)) {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedInvalido");
					lblFeed.setText("Formato email no válido");
				} else {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedValido");
					lblFeed.setText("Email válido");
				}
			} else {
				lblFeed.setText(" ");
			}
		});
	}

	private boolean validarRegistro() {

		if (txtNombre.getText() == null || txtNombre.getText().isEmpty()) {
			alertas.alertaError("Error de validación", "El nombre de peregrino no puede estar vacío.");
			return false;
		}

		if (txtEmail.getText() == null || txtEmail.getText().isEmpty()) {
			alertas.alertaError("Error de validación", "El email no puede estar vacío.");
			return false;
		}

		return true;

	}

}
