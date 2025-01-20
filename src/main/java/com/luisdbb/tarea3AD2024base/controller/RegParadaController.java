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
import com.luisdbb.tarea3AD2024base.config.Validaciones;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class RegParadaController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private TextField txtUsuario;

	@FXML
	private TextField txtEmail;

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
	private TextField txtNombreP;

	@FXML
	private TextField txtRegionP;

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
	private final StringProperty usuarioProperty = new SimpleStringProperty();
	private final StringProperty emailProperty = new SimpleStringProperty();
	private final StringProperty regionPProperty = new SimpleStringProperty();
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
	private UsuarioService usuarioService;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private Validaciones validaciones;

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
	 * Handler del botón btnRegistrar. Método que registra un usuario y una parada y
	 * muestra mensajes de alerta para exito o error.
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

			usuarioService.registrarUsuarioYParada(txtUsuario.getText(), txtEmail.getText(), contraseña,
					txtNombreP.getText(), txtRegionP.getText().charAt(0));

			alertas.alertaInformacion("Registro exitoso",
					"Se han registrado el usuario responsable de parada y la parada correctamente.\n"
							+ "\nVolviendo a menú de administrador.");

			stageManager.switchScene(FxmlView.ADMIN);

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
			txtUsuario.clear();
			txtEmail.clear();
			txtNombreP.clear();
			txtRegionP.clear();
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
	 * de Administración.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.ADMIN);
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

		// asociación de fxml con properties
		usuarioProperty.bind(txtUsuario.textProperty());
		emailProperty.bind(txtEmail.textProperty());
		regionPProperty.bind(txtRegionP.textProperty());

		if (passContraseña.isVisible()) {
			contraseñaProperty.bind(passContraseña.textProperty());
		} else {
			contraseñaProperty.bind(txtContraseña.textProperty());
		}

		passContraseña.visibleProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				contraseñaProperty.unbind();
				contraseñaProperty.bind(passContraseña.textProperty());
			} else {
				contraseñaProperty.bind(txtContraseña.textProperty());
			}
		});

		usuarioProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!validaciones.validarEspacios(newValue)) {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedInvalido");
					lblFeed.setText("Usuario sin espacios en blanco");
				} else if (usuarioService.existsByNombreUsuario(newValue)) {
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

		regionPProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!validaciones.validarRegion(newValue)) {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedInvalido");
					lblFeed.setText("La región es un único caracter");
				} else {
					lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
					lblFeed.getStyleClass().add("lblFeedValido");
					lblFeed.setText("Región válida");
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
					lblFeed.setText("Min 6 carac.: una mayúscula, un nº y un c. especial");
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
	 * Método que valida los campos del formulario, que no sean null o estén vacíos,
	 * que el nombre de la parada no exista ya en esa región y que la contraseña
	 * coincida con su confirmación.
	 * 
	 * @return true si todos los campos están rellenados, la parada no existe ya y
	 *         las contraseñas coinciden
	 */
	private boolean validarRegistro() {
		if (txtUsuario.getText() == null || txtUsuario.getText().isEmpty()) {
			alertas.alertaError("Error de validación", "El nombre de usuario no puede estar vacío.");
			return false;
		}

		if (txtEmail.getText() == null || txtEmail.getText().isEmpty()) {
			alertas.alertaError("Error de validación", "El email no puede estar vacío.");
			return false;
		}

		if (txtNombreP.getText() == null || txtNombreP.getText().isEmpty()) {
			alertas.alertaError("Error de validación", "El nombre de la parada no puede estar vacío.");
			return false;
		}

		if (txtRegionP.getText() == null || txtRegionP.getText().isEmpty()) {
			alertas.alertaError("Error de validación", "La región de la parada no puede estar vacía.");
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

		if (paradaService.existeParada(txtNombreP.getText(), txtRegionP.getText().charAt(0))) {
			alertas.alertaError("Error en Parada", "El nombre de la parada ya existe en esa región.");
			return false;
		}

		if (txtConfirmacion.isVisible()) {
			if (!validaciones.validarContraseñas(txtContraseña.getText(), txtConfirmacion.getText())) {
				alertas.alertaError("Error de contraseña",
						"La confirmación de la contraseña no coincide con la contraseña ingresada.");
				return false;
			}
		}

		if (passConfirmacion.isVisible()) {
			if (!validaciones.validarContraseñas(passContraseña.getText(), passConfirmacion.getText())) {
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
