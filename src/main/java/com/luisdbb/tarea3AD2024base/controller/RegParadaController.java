package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.Alertas;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.config.Validaciones;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	private TextField txtConfirmacion;

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
	private UsuarioService usuarioService;

	@Autowired
	private ParadaService paradaService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		validarEntradas();

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
			
			Usuario user = new Usuario(txtUsuario.getText(), txtEmail.getText(), txtContraseña.getText(),
					Perfil.PARADA);

			Parada parada = new Parada(txtNombreP.getText(), txtRegionP.getText().charAt(0), user);

			usuarioService.registrarUsuarioParada(user, parada);
			Alertas.alertaInformacion("Registro exitoso",
					"Se han registrado el usuario responsable de parada y la parada correctamente.");
			stageManager.switchScene(FxmlView.ADMIN);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			Alertas.alertaInformacion("Error",
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
			txtUsuario.clear();
			txtEmail.clear();
			txtNombreP.clear();
			txtRegionP.clear();
			txtContraseña.clear();
			txtConfirmacion.clear();
		} else {
			Alertas.alertaInformacion("Acción cancelada", "Por favor, rellene el formulario.");
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

		// asociación de fxml con properties
		usuarioProperty.bind(txtUsuario.textProperty());
		emailProperty.bind(txtEmail.textProperty());
		regionPProperty.bind(txtRegionP.textProperty());
		contraseñaProperty.bind(txtContraseña.textProperty());

		usuarioProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!Validaciones.validarEspacios(newValue)) {
					lblFeed.setText("Usuario sin espacios en blanco");
				} else if (usuarioService.findByUsuario(newValue) != null) {
					lblFeed.setText("El usuario ya existe");
				} else {
					lblFeed.setText("Usuario válido");
				}
			} else {
				lblFeed.setText(" ");
			}
		});

		emailProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!Validaciones.validarEspacios(newValue)) {
					lblFeed.setText("Email sin espacios en blanco");
				} else if (!Validaciones.validarEmail(newValue)) {
					lblFeed.setText("Formato email no válido");
				} else {
					lblFeed.setText("Email válido");
				}
			} else {
				lblFeed.setText(" ");
			}
		});

		regionPProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (newValue.length() != 1) {
					lblFeed.setText("La región es un único caracter");
				} else {
					lblFeed.setText("Región válida");
				}
			} else {
				lblFeed.setText(" ");
			}
		});

		contraseñaProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!Validaciones.validarEspacios(newValue)) {
					lblFeed.setText("Contraseña sin espacios en blanco");
				} else if (!Validaciones.validarContraseña(newValue)) {
					lblFeed.setText("Min 6 caracteres: mayúscula, nº y especial");
				} else {
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
			Alertas.alertaInformacion("Error de validación", "El nombre de usuario no puede estar vacío.");
			return false;
		}

		if (txtEmail.getText() == null || txtEmail.getText().isEmpty()) {
			Alertas.alertaInformacion("Error de validación", "El email no puede estar vacío.");
			return false;
		}

		if (txtNombreP.getText() == null || txtNombreP.getText().isEmpty()) {
			Alertas.alertaInformacion("Error de validación", "El nombre de la parada no puede estar vacío.");
			return false;
		}

		if (txtRegionP.getText() == null || txtRegionP.getText().isEmpty()) {
			Alertas.alertaInformacion("Error de validación", "La región de la parada no puede estar vacía.");
			return false;
		}

		if (txtContraseña.getText() == null || txtContraseña.getText().isEmpty()) {
			Alertas.alertaInformacion("Error de validación", "La contraseña no puede estar vacía.");
			return false;
		}

		if (txtConfirmacion.getText() == null || txtConfirmacion.getText().isEmpty()) {
			Alertas.alertaInformacion("Error de validación", "La confirmación de la contraseña es obligatoria.");
			return false;
		}

		if (paradaService.existeParada(txtNombreP.getText(), txtRegionP.getText().charAt(0))) {
			Alertas.alertaInformacion("Error en Parada", "El nombre de la parada ya existe en esa región.");
			return false;
		}

		if (!txtConfirmacion.getText().equals(txtContraseña.getText())) {
			Alertas.alertaInformacion("Error de contraseña",
					"La confirmación de la contraseña no coincide con la contraseña ingresada.");
			return false;
		}

		return true;
	}

}
