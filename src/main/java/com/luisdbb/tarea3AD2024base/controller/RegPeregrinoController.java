package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.Alertas;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.config.Validaciones;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
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
	private DatePicker dateFecha;

	@FXML
	private RadioButton rbMasc;

	@FXML
	private RadioButton rbFem;

	@FXML
	private ToggleGroup tgGenero;

	@FXML
	private ComboBox<String> cmbNacionalidad;

	ObservableList<String> listaNac = FXCollections.observableArrayList("Opción A", "Opción B", "Opción C");

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtUsuario;

	@FXML
	private TextField txtContraseña;

	@FXML
	private TextField txtConfirmacion;

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
	private ParadaService paradaService;

	@Autowired
	private PeregrinoService peregrinoService;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		validarEntradas();

		// combobox
		listaParadas = FXCollections.observableArrayList(paradaService.findAll());
		cmbParada.setItems(listaParadas);

		cmbNacionalidad.setItems(listaNac);

		// toggle genero
		tgGenero = new ToggleGroup();
		rbMasc.setToggleGroup(tgGenero);
		rbFem.setToggleGroup(tgGenero);

		// config info
		String rutaInfo = resources.getString("info.icon");
		Image imagen = new Image(getClass().getResourceAsStream(rutaInfo));
		ImageView imageView = new ImageView(imagen);
		imageView.setFitWidth(30);
		imageView.setFitHeight(30);
		imageView.setPreserveRatio(true);
		hpInfo.setGraphic(imageView);

		// config img btn Limpiar
		String rutaLimp = resources.getString("btnLimpiar.icon");
		Image imgLimp = new Image(getClass().getResourceAsStream(rutaLimp));
		ImageView viewLimp = new ImageView(imgLimp);
		viewLimp.setFitWidth(30);
		viewLimp.setFitHeight(30);
		btnLimpiar.setGraphic(viewLimp);

		// config img btn Registrar
		String rutaReg = resources.getString("btnRegistrar.icon");
		Image imgReg = new Image(getClass().getResourceAsStream(rutaReg));
		ImageView viewReg = new ImageView(imgReg);
		viewReg.setFitWidth(60);
		viewReg.setFitHeight(30);
		btnRegistrar.setGraphic(viewReg);

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
		btnLimpiar.setTooltip(new Tooltip("Limpiar (Alt+L)"));
		btnRegistrar.setTooltip(new Tooltip("Registrar (Alt+R)"));
		btnVolver.setTooltip(new Tooltip("Volver (Alt+V)"));
		btnSalir.setTooltip(new Tooltip("Salir (Alt+S)"));

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

			Usuario usuario = new Usuario(txtUsuario.getText(), txtEmail.getText(), txtContraseña.getText(),
					Perfil.PEREGRINO);

			Parada paradaInicial = cmbParada.getSelectionModel().getSelectedItem();
			Carnet carnet = new Carnet(paradaInicial);

			RadioButton generoSeleccionado = (RadioButton) tgGenero.getSelectedToggle();
			String genero = generoSeleccionado.getText();

			Peregrino peregrino = new Peregrino(txtNombre.getText(), txtApellidos.getText(), dateFecha.getValue(),
					genero, cmbNacionalidad.getSelectionModel().getSelectedItem(), usuario, carnet);

			carnet.setPeregrino(peregrino);

			peregrinoService.save(peregrino);

			Alertas.alertaInformacion("Registro exitoso",
					"Se ha registrado como peregrino y se ha creado su carnet correctamente.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			Alertas.alertaError("Error",
					"Hubo un problema al registrar los datos. Por favor, revise la información.");

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

		boolean confirmar = Alertas.alertaConfirmacion("Borado de formulario",
				"Se borrarán los datos introducidos, ¿está de acuerdo?");

		if (confirmar) {
			cmbParada.getSelectionModel().clearSelection();
			txtNombre.clear();
			txtApellidos.clear();
			dateFecha.setValue(null);
			tgGenero.selectToggle(null);
			cmbNacionalidad.getSelectionModel().clearSelection();
			txtEmail.clear();
			txtUsuario.clear();
			txtContraseña.clear();
			txtConfirmacion.clear();
		} else {
			Alertas.alertaInformacion("Acción cancelada", "Por favor, rellene el formulario.");
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

	/**
	 * Método que valida cada campo del formulario y muestra el error en lblFeed
	 */
	private void validarEntradas() {

		lblFeed.setText(" ");

		// asociación de property con fxml
		ObjectProperty<LocalDate> fechaProperty = dateFecha.valueProperty();
		emailProperty.bind(txtEmail.textProperty());
		usuarioProperty.bind(txtUsuario.textProperty());
		contraseñaProperty.bind(txtContraseña.textProperty());

		fechaProperty.addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				LocalDate hoy = LocalDate.now();
				if (newValue.isAfter(hoy)) {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedInvalido");
					lblFeed.setText("La fecha no puede ser posterior al día de hoy.");
				} else {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedValido");
					lblFeed.setText("Fecha válida.");
				}
			} else {
				lblFeed.setText(" ");
			}
		});

		emailProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!Validaciones.validarEspacios(newValue)) {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedInvalido");
					lblFeed.setText("Email sin espacios en blanco");
				} else if (!Validaciones.validarEmail(newValue)) {
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
				if (!Validaciones.validarEspacios(newValue)) {
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
				if (!Validaciones.validarEspacios(newValue)) {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedInvalido");
					lblFeed.setText("Contraseña sin espacios en blanco");
				} else if (!Validaciones.validarContraseña(newValue)) {
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
			Alertas.alertaError("Error de validación", "La parada inicial no puede estar vacía.");
			return false;
		}

		if (txtNombre.getText() == null || txtNombre.getText().isEmpty()) {
			Alertas.alertaError("Error de validación", "El nombre de peregrino no puede estar vacío.");
			return false;
		}

		if (txtEmail.getText() == null || txtEmail.getText().isEmpty()) {
			Alertas.alertaError("Error de validación", "El email no puede estar vacío.");
			return false;
		}

		if (txtUsuario.getText() == null || txtUsuario.getText().isEmpty()) {
			Alertas.alertaError("Error de validación", "El nombre de usuario no puede estar vacío.");
			return false;
		}

		if (txtContraseña.getText() == null || txtContraseña.getText().isEmpty()) {
			Alertas.alertaError("Error de validación", "La contraseña no puede estar vacía.");
			return false;
		}

		if (txtConfirmacion.getText() == null || txtConfirmacion.getText().isEmpty()) {
			Alertas.alertaError("Error de validación", "La confirmación de la contraseña es obligatoria.");
			return false;
		}

		if (!txtConfirmacion.getText().equals(txtContraseña.getText())) {
			Alertas.alertaError("Error de contraseña",
					"La confirmación de la contraseña no coincide con la contraseña ingresada.");
			return false;
		}

		return true;
	}

}
