package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.Alertas;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
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
public class RecuperacionController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private TextField txtUsuario;

	// propiedades usuario
	private final StringProperty usuarioProperty = new SimpleStringProperty();

	@FXML
	private Label lblEmail;

	@FXML
	private Button btnEnviar;

	@FXML
	private Button btnVolver;

	@FXML
	private Button btnSalir;

	// inyecciones
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private Alertas alertas;

	@Autowired
	private UsuarioService usuarioService;

	private boolean existeUsuario = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// config txtUsuario
		usuarioProperty.bind(txtUsuario.textProperty());
		usuarioProperty.addListener((observable, oldValue, newValue) -> {
			Usuario user = usuarioService.findByUsuario(txtUsuario.getText());
			if (txtUsuario.getText().isEmpty()) {
				lblEmail.setText("Email");
				existeUsuario = false;
			} else if (!usuarioService.existsByNombreUsuario(newValue)) {
				lblEmail.setText("El usuario no existe");
				existeUsuario = false;
			} else {
				lblEmail.setText(user.getEmail());
				existeUsuario = true;
			}
		});

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

		// config img info
		String rutaInfo = resources.getString("info.icon");
		Image imagen = new Image(getClass().getResourceAsStream(rutaInfo));
		ImageView imageView = new ImageView(imagen);
		imageView.setFitWidth(50);
		imageView.setFitHeight(50);
		imageView.setPreserveRatio(true);
		hpInfo.setGraphic(imageView);

		// mnemónicos
		hpInfo.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.I) {
				hpInfo.fire();
				event.consume();
			}
		});

		btnEnviar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.E) {
				btnEnviar.fire();
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
		btnEnviar.setTooltip(new Tooltip("Enviar (Alt+E)"));
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

	/**
	 * Handler para el botón `btnEnviar`. Este método se ejecuta al pulsar el botón
	 * para enviar la contraseña al correo electrónico del usuario.
	 * 
	 * Realiza las siguientes acciones: - Verifica si el usuario existe en la base
	 * de datos mediante la variable `existeUsuario`: - Si el usuario existe: -
	 * Muestra una alerta de confirmación preguntando al usuario si desea enviar la
	 * contraseña al correo asociado. - Si el usuario confirma, muestra un mensaje
	 * de éxito indicando que se ha enviado el correo electrónico y cambia la escena
	 * al formulario de inicio de sesión (`FxmlView.LOGIN`) utilizando el
	 * `stageManager`. - Si el usuario cancela, muestra un mensaje de cancelación
	 * indicando que debe introducir un usuario registrado. - Si el usuario no
	 * existe, muestra un mensaje indicando que es necesario un usuario registrado
	 * para recuperar la contraseña.
	 * 
	 * @param event El evento que dispara el método (clic en el botón).
	 * @throws IOException Si ocurre un error al cambiar la escena o al acceder a
	 *                     recursos de E/S.
	 */

	@FXML
	private void handlerEnviar(ActionEvent event) throws IOException {
		if (existeUsuario) {
			boolean respuesta = alertas.alertaConfirmacion("Confirmación email",
					"Se le enviará su contraseña a este email.\n¿Es corecto?");
			if (respuesta) {
				alertas.alertaInformacion("Email enviado", "Se le ha enviado el Email.\n\nVolviendo al menú.");
				stageManager.switchScene(FxmlView.LOGIN);
			} else {
				alertas.alertaInformacion("Acción cancelada", "Por favor, introduzca un usuario registrado.");
			}

		} else {
			alertas.alertaError("Falta usuario",
					"Es necesario un usuario registrado para recuperar la contraseña");
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

}
