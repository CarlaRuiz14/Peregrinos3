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
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.services.NacionalidadService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Controller
public class RegPeregrinoController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private ComboBox<Parada> cmbParada;

	ObservableList<Parada> listaParadas;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtApellidos;

	@FXML
	private ComboBox<String> cmbNacionalidad;

	ObservableList<String> listaNac;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtUsuario;

	@FXML
	private TextField txtContraseña;

	@FXML
	private PasswordField passContraseña;

	@FXML
	private Hyperlink hpVisible;

	@FXML
	private TextField txtConfirmacion;

	@FXML
	private PasswordField passConfirmacion;

	@FXML
	private Button btnLimpiar;

	@FXML
	private Button btnRegistrar;

	@FXML
	private Button btnVolver;

	@FXML
	private Label lblFeed;

	@FXML
	private Button btnSalir;

	// propiedades

	private final StringProperty emailProperty = new SimpleStringProperty();
	private final StringProperty usuarioProperty = new SimpleStringProperty();
	private final StringProperty contraseñaProperty = new SimpleStringProperty();

	// inyecciones
	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private Alertas alertas;

	@Autowired
	private BotonesConfig botones;
	
	@Autowired
	private AyudaConfig ayuda;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private PeregrinoService peregrinoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private Validaciones validaciones;

	@Autowired
	private NacionalidadService nacionalidadService;

	// conf hpVisible
	private boolean isPassVisible = false;
	private Image mostrarIcon;
	private Image ocultarIcon;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		validarEntradas();

		// config hpVisible inicial
		String mostrarPath = resources.getString("contraseñaC.icon");
		String ocultarPath = resources.getString("contraseñaO.icon");

		mostrarIcon = new Image(getClass().getResourceAsStream(mostrarPath));
		ocultarIcon = new Image(getClass().getResourceAsStream(ocultarPath));

		hpVisible.setGraphic(createImageView(mostrarIcon));

		// combobox
		listaParadas = FXCollections.observableArrayList(paradaService.findAll());
		cmbParada.setItems(listaParadas);

		List<String> listaValores = new ArrayList<>(nacionalidadService.mapaNacionalidades().values());
		listaNac = FXCollections.observableArrayList(listaValores);
		cmbNacionalidad.setItems(listaNac);

		// config info
		ayuda.configImgInfo(hpInfo);

		// config img btn Limpiar
		botones.configImgLimpiar(btnLimpiar);

		// config img btn Registrar
		botones.configImgFlecha(btnRegistrar);

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
		hpVisible.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.M) {
				hpVisible.fire();
				event.consume();
			}
		});

		btnLimpiar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.L) {
				btnLimpiar.fire();
				event.consume();
			}
		});

		btnRegistrar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.R) {
				btnRegistrar.fire();
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
		hpVisible.setTooltip(new Tooltip("Mostrar (Alt+M)"));
		btnLimpiar.setTooltip(new Tooltip("Limpiar (Alt+L)"));
		btnRegistrar.setTooltip(new Tooltip("Registrar (Alt+R)"));
		btnVolver.setTooltip(new Tooltip("Volver (Alt+V)"));
		btnSalir.setTooltip(new Tooltip("Salir (Alt+S)"));

	}

	@FXML
	private void handlerVisible() {
		isPassVisible = !isPassVisible;
		if (isPassVisible) {
			txtContraseña.setText(passContraseña.getText());
			txtContraseña.setVisible(true);
			passContraseña.setVisible(false);

			txtConfirmacion.setText(passConfirmacion.getText());
			txtConfirmacion.setVisible(true);
			passConfirmacion.setVisible(false);
			hpVisible.setGraphic(createImageView(ocultarIcon));
		} else {
			passContraseña.setText(txtContraseña.getText());
			txtContraseña.setVisible(false);
			passContraseña.setVisible(true);

			passConfirmacion.setText(txtConfirmacion.getText());
			txtConfirmacion.setVisible(false);
			passConfirmacion.setVisible(true);
			hpVisible.setGraphic(createImageView(mostrarIcon));
		}

	}

	/**
	 * Handler del botón btnRegistrar. Método que registra un usuario, un carnet y
	 * un peregrino y muestra mensajes de alerta para exito o error.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlerRegistrar(ActionEvent event) throws IOException {

		try {
			if (!validarRegistro()) {
				return;
			}

			String contraseña = passContraseña.isVisible() ? passContraseña.getText() : txtContraseña.getText();
			Parada paradaInicial = cmbParada.getSelectionModel().getSelectedItem();
			String nacionalidad = nacionalidadService
					.obtenerNacionalidadSeleccionada(cmbNacionalidad.getSelectionModel().getSelectedItem());

			peregrinoService.registrarUsuarioCarnetYPeregrino(txtUsuario.getText(), txtEmail.getText(), contraseña,
					paradaInicial, txtNombre.getText(), txtApellidos.getText(), nacionalidad);

			alertas.alertaInformacion("Registro exitoso",
					"Se ha registrado como peregrino y se ha creado su carnet correctamente.\n\nVolviendo al login.");
			stageManager.switchScene(FxmlView.LOGIN);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			alertas.alertaError("Error", "Hubo un problema al registrar los datos. Por favor, revise la información.");

		}

	}

	/**
	 * Handler para el botón btnLimpiar. Método que al pulsarlo limpia los campos
	 * del formulario.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlerLimpiar(ActionEvent event) throws IOException {

		boolean confirmar = alertas.alertaConfirmacion("Borado de formulario",
				"Se borrarán los datos introducidos, ¿está de acuerdo?");

		if (confirmar) {
			cmbParada.getSelectionModel().clearSelection();
			txtNombre.clear();
			txtApellidos.clear();
			cmbNacionalidad.getSelectionModel().clearSelection();
			txtEmail.clear();
			txtUsuario.clear();
			txtContraseña.clear();
			txtConfirmacion.clear();
			passContraseña.clear();
			passConfirmacion.clear();
		} else {
			alertas.alertaInformacion("Acción cancelada", "Por favor, rellene el formulario.");
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
		stageManager.switchScene(FxmlView.LOGIN);
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

	/**
	 * Método que valida cada campo del formulario y muestra el error en lblFeed
	 */
	private void validarEntradas() {

		lblFeed.setText(" ");

		// asociación de property con fxml
		emailProperty.bind(txtEmail.textProperty());
		usuarioProperty.bind(txtUsuario.textProperty());
		contraseñaProperty.bind(txtContraseña.textProperty());

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

		usuarioProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!validaciones.validarEspacios(newValue)) {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedInvalido");
					lblFeed.setText("Usuario sin espacios en blanco");
				} else if (usuarioService.findByUsuario(newValue) != null) {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedInvalido");
					lblFeed.setText("El usuario ya existe");
				} else {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedValido");
					lblFeed.setText("Usuario válido");
				}
			} else {
				lblFeed.setText(" ");
			}
		});

		contraseñaProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!validaciones.validarEspacios(newValue)) {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedInvalido");
					lblFeed.setText("Contraseña sin espacios en blanco");
				} else if (!validaciones.validarContraseña(newValue)) {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedInvalido");
					lblFeed.setText("Min 6 caracteres: mayúscula, nº y especial");
				} else {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedValido");
					lblFeed.setText("Contraseña válida");
				}
			} else {
				lblFeed.setText(" ");
			}
		});

	}

	/**
	 * Método que valida que los campos obligatorios del formulartio no sean null o
	 * estén vacios. Estos campos son parada inicial, nombre, email,usuario,
	 * contraseña y su confirmación. Además, comprueba que la contraseña coincida
	 * con su confirmación.
	 * 
	 * @return true si todos los campos están rellenados y las contraseñas coinciden
	 */
	private boolean validarRegistro() {

		if (cmbParada.getSelectionModel().getSelectedItem() == null) {
			alertas.alertaError("Error de validación", "La parada inicial no puede estar vacía.");
			return false;
		}

		if (txtNombre.getText() == null || txtNombre.getText().isEmpty()) {
			alertas.alertaError("Error de validación", "El nombre de peregrino no puede estar vacío.");
			return false;
		}

		if (txtEmail.getText() == null || txtEmail.getText().isEmpty()) {
			alertas.alertaError("Error de validación", "El email no puede estar vacío.");
			return false;
		}

		if (txtUsuario.getText() == null || txtUsuario.getText().isEmpty()) {
			alertas.alertaError("Error de validación", "El nombre de usuario no puede estar vacío.");
			return false;
		}

		if (txtContraseña.isVisible()) {
			if (txtContraseña.getText() == null || txtContraseña.getText().isEmpty()) {
				alertas.alertaError("Error de validación", "La contraseña no puede estar vacía.");
				return false;
			}
			if (txtConfirmacion.getText() == null || txtConfirmacion.getText().isEmpty()) {
				alertas.alertaError("Error de validación", "La confirmación de la contraseña es obligatoria.");
				return false;
			}
		}

		if (passContraseña.isVisible()) {
			if (passContraseña.getText() == null || passContraseña.getText().isEmpty()) {
				alertas.alertaError("Error de validación", "La contraseña no puede estar vacía.");
				return false;
			}
			if (passConfirmacion.getText() == null || passConfirmacion.getText().isEmpty()) {
				alertas.alertaError("Error de validación", "La confirmación de la contraseña es obligatoria.");
				return false;
			}
		}

		if (txtConfirmacion.isVisible()) {
			if (!txtConfirmacion.getText().equals(txtContraseña.getText())) {
				alertas.alertaError("Error de contraseña",
						"La confirmación de la contraseña no coincide con la contraseña ingresada.");
				return false;
			}
		}

		if (passConfirmacion.isVisible()) {
			if (!passConfirmacion.getText().equals(passContraseña.getText())) {
				alertas.alertaError("Error de contraseña",
						"La confirmación de la contraseña no coincide con la contraseña ingresada.");
				return false;

			}
		}

		return true;
	}

	/**
	 * Crea un ImageView con un tamaño fijo de 30x30 píxeles.
	 *
	 * @param image La imagen que se asignará al ImageView.
	 * @return Un ImageView con las dimensiones ajustadas.
	 */
	private ImageView createImageView(Image image) {
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(30);
		imageView.setFitHeight(30);
		imageView.setPreserveRatio(true);
		return imageView;
	}

}
