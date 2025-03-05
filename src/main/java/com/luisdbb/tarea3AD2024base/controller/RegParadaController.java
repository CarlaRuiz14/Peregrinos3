package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;
import com.luisdbb.tarea3AD2024base.services.Validaciones;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * RegParadaController es el controlador para el registro de un usuario
 * responsable de una parada.
 * 
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

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private Alertas alertas;

	@Autowired
	private Botones botones;

	@Autowired
	private Ayuda ayuda;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private Validaciones validaciones;

	@Autowired
	private Mnemonic mnemonicConfig;

	@Autowired
	private Tooltips tooltipConfig;

	@Autowired
	private LabelFeed label;

	private final StringProperty usuarioProperty = new SimpleStringProperty();
	private final StringProperty emailProperty = new SimpleStringProperty();
	private final StringProperty regionPProperty = new SimpleStringProperty();
	private final StringProperty contraseñaProperty = new SimpleStringProperty();

	private boolean isPassVisible = false;
	private Image mostrarIcon;
	private Image ocultarIcon;

	private boolean usuarioCheck = false;
	private boolean emailCheck = false;
	private boolean regionCheck = false;
	private boolean contraseñaCheck = false;

	/**
	 * Inicializa el controlador y configura la interfaz del registro de parada.
	 * <ul>
	 * <li>Configura la validación de las entradas: usuario, email, región y
	 * contraseña.</li>
	 * <li>Carga los iconos para mostrar y ocultar la contraseña y establece el
	 * estado inicial.</li>
	 * <li>Configura los botones con imágenes, atajos de teclado y tooltips.</li>
	 * </ul>
	 *
	 * @param location  La URL utilizada para resolver el objeto raíz, o null.
	 * @param resources Los recursos de localización o null.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		validarEntradas();

		String mostrarPath = resources.getString("contraseñaC.icon");
		String ocultarPath = resources.getString("contraseñaO.icon");

		mostrarIcon = new Image(getClass().getResourceAsStream(mostrarPath));
		ocultarIcon = new Image(getClass().getResourceAsStream(ocultarPath));

		hpVisible.setGraphic(botones.createImageView(mostrarIcon));

		ayuda.configImgInfo(hpInfo);
		botones.imgLimpiar(btnLimpiar);
		botones.imgFlecha(btnRegistrar);
		botones.imgVolver(btnVolver);
		botones.imgSalir(btnSalir);

		mnemonicConfig.infoMnemonic(hpInfo);
		mnemonicConfig.visibleMnemonic(hpVisible);
		mnemonicConfig.limpiarMnemonic(btnLimpiar);
		btnRegistrar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
				btnRegistrar.fire();
				event.consume();
			}
		});
		mnemonicConfig.volverMnemonic(btnVolver);
		mnemonicConfig.salirMnemonic(btnSalir);

		tooltipConfig.salirTooltip(btnSalir);
		tooltipConfig.visibleTooltip(hpVisible);
		tooltipConfig.limpiarTooltip(btnLimpiar);
		btnRegistrar.setTooltip(new Tooltip("Registrar (Alt+Enter)"));
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);
	}

	/**
	 * Muestra la ayuda relacionada con el registro de parada.
	 * 
	 * @param event Evento de acción disparado al hacer clic en el enlace de ayuda.
	 * @throws IOException Si ocurre un error al cargar la ayuda.
	 */
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/regParada.html", stage);
	}

	/**
	 * Alterna la visibilidad de los campos de contraseña.
	 * <ul>
	 * <li>Si la contraseña es visible, se ocultan y se muestra el icono para
	 * "mostrar".</li>
	 * <li>Si la contraseña está oculta, se muestran en un campo de texto y se
	 * cambia el icono a "ocultar".</li>
	 * </ul>
	 */
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
			hpVisible.setGraphic(botones.createImageView(ocultarIcon));
		} else {
			passContraseña.setText(txtContraseña.getText());
			txtContraseña.setVisible(false);
			passContraseña.setVisible(true);

			passConfirmacion.setText(txtConfirmacion.getText());
			txtConfirmacion.setVisible(false);
			passConfirmacion.setVisible(true);
			hpVisible.setGraphic(botones.createImageView(mostrarIcon));
		}
	}

	/**
	 * Procesa el registro del usuario responsable de una parada.
	 * <ul>
	 * <li>Verifica que cada campo sea válido (usuario, email, región y
	 * contraseña).</li>
	 * <li>Llama al servicio para registrar el usuario y la parada.</li>
	 * <li>Notifica al usuario del registro exitoso y redirige a la vista de
	 * administrador.</li>
	 * </ul>
	 *
	 * @param event Evento de acción disparado al hacer clic en "Registrar".
	 * @throws IOException Si ocurre un error durante el registro.
	 */
	@FXML
	private void handlerRegistrar(ActionEvent event) throws IOException {

		try {

			if (!usuarioCheck) {
				alertas.alertaError("Error", "El usuario no es válido.");
				return;
			}

			if (!emailCheck) {
				alertas.alertaError("Error", "El email no es válido.");
				return;
			}

			if (!regionCheck) {
				alertas.alertaError("Error", "La región no es válida.");
				return;
			}

			if (!contraseñaCheck) {
				alertas.alertaError("Error", "La contraseña no es válida.");
				return;
			}

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
			e.printStackTrace();
			alertas.alertaError("Error", "Hubo un problema al registrar los datos. Por favor, revise la información.");
			return;
		}
	}

	/**
	 * Limpia el formulario de registro.
	 * <ul>
	 * <li>Pide confirmación al usuario antes de borrar los datos ingresados.</li>
	 * <li>Si se confirma, limpia todos los campos del formulario.</li>
	 * <li>Si se cancela, notifica al usuario que la acción fue cancelada.</li>
	 * </ul>
	 *
	 * @param event Evento de acción disparado al hacer clic en "Limpiar".
	 * @throws IOException Si ocurre un error durante la acción.
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
	 * Configura la validación de las entradas del formulario de registro.
	 * 
	 */
	private void validarEntradas() {

		lblFeed.setText(" ");

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
					label.mostrarTxtInvalido(lblFeed, "Usuario sin espacios en blanco");
					usuarioCheck = false;
				} else if (usuarioService.existsByNombreUsuario(newValue)) {
					label.mostrarTxtInvalido(lblFeed, "El usuario ya existe");
					usuarioCheck = false;
				} else {
					label.mostrarTxtValido(lblFeed, "Usuario válido");
					usuarioCheck = true;
				}
			} else {
				lblFeed.setText(" ");
				usuarioCheck = true;
			}
		});

		emailProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!validaciones.validarEspacios(newValue)) {
					label.mostrarTxtInvalido(lblFeed, "Email sin espacios en blanco");
					emailCheck = false;
				} else if (!validaciones.validarEmail(newValue)) {
					label.mostrarTxtInvalido(lblFeed, "Formato no válido. Formato email: __@__.__");
					emailCheck = false;
				} else {
					label.mostrarTxtValido(lblFeed, "Email válido.");
					emailCheck = true;
				}
			} else {
				lblFeed.setText(" ");
				emailCheck = true;
			}
		});

		regionPProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!validaciones.validarRegion(newValue)) {
					label.mostrarTxtInvalido(lblFeed, "La región es una única letra.");
					regionCheck = false;
				} else {
					label.mostrarTxtValido(lblFeed, "Región válida");
					regionCheck = true;
				}
			} else {
				lblFeed.setText(" ");
				regionCheck = true;
			}
		});

		contraseñaProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!validaciones.validarEspacios(newValue)) {
					label.mostrarTxtInvalido(lblFeed, "Contraseña sin espacios en blanco");
					contraseñaCheck = false;
				} else if (!validaciones.validarContraseña(newValue)) {
					label.mostrarTxtInvalido(lblFeed, "Min 6 carac: 1 min, 1 may, 1 nº y 1 carac. especial");
					contraseñaCheck = false;
				} else {
					label.mostrarTxtValido(lblFeed, "Contraseña válida");
					contraseñaCheck = true;
				}
			} else {
				lblFeed.setText(" ");
				contraseñaCheck = true;
			}
		});

	}

	/**
	 * Valida que los campos obligatorios del registro estén completos y correctos.
	 *
	 * @return true si la validación es exitosa, false en caso contrario.
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
}
