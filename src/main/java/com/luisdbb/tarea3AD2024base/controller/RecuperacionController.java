package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * RecuperacionController es el controlador para la recuperación de contraseñas.
 * 
 * @author Carla
 * @since 28/12/2024
 */
@Controller
public class RecuperacionController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private TextField txtUsuario;

	private final StringProperty usuarioProperty = new SimpleStringProperty();

	@FXML
	private Label lblEmail;

	@FXML
	private Button btnEnviar;

	@FXML
	private Button btnVolver;

	@FXML
	private Button btnSalir;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private Alertas alertas;

	@Autowired
	private Ayuda ayuda;

	@Autowired
	private Botones botones;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private Mnemonic mnemonicConfig;

	@Autowired
	private Tooltips tooltipConfig;

	private boolean existeUsuario = false;

	/**
	 * Inicializa el controlador.
	 * <ul>
	 * <li>Asocia el contenido del campo txtUsuario a una propiedad y escucha
	 * cambios.</li>
	 * <li>Muestra el email del usuario si este existe; en caso contrario, indica
	 * que no existe.</li>
	 * <li>Configura los iconos de los botones de enviar, volver y salir.</li>
	 * <li>Establece atajos de teclado y tooltips para mejorar la usabilidad.</li>
	 * </ul>
	 *
	 * @param location  La URL utilizada para resolver el objeto raíz o null.
	 * @param resources Los recursos de localización o null.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

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

		botones.imgFlecha(btnEnviar);
		botones.imgVolver(btnVolver);
		botones.imgSalir(btnSalir);

		String rutaInfo = resources.getString("info.icon");
		Image imagen = new Image(getClass().getResourceAsStream(rutaInfo));
		ImageView imageView = new ImageView(imagen);
		imageView.setFitWidth(50);
		imageView.setFitHeight(50);
		imageView.setPreserveRatio(true);
		hpInfo.setGraphic(imageView);

		mnemonicConfig.infoMnemonic(hpInfo);
		btnEnviar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
				btnEnviar.fire();
				event.consume();
			}
		});
		mnemonicConfig.volverMnemonic(btnVolver);
		mnemonicConfig.salirMnemonic(btnSalir);

		tooltipConfig.salirTooltip(btnSalir);
		btnEnviar.setTooltip(new Tooltip("Enviar (Alt+Enter)"));
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);
	}

	/**
	 * Muestra la ayuda de la recuperación de contraseña.
	 *
	 * @param event Evento de acción disparado al hacer clic en el enlace de ayuda.
	 * @throws IOException Si ocurre un error al cargar el recurso de ayuda.
	 */
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/recContraseña.html", stage);
	}

	/**
	 * Envía la solicitud de recuperación de contraseña.
	 * <ul>
	 * <li>Verifica que el usuario ingresado exista.</li>
	 * <li>Confirma con el usuario el envío del correo de recuperación.</li>
	 * <li>En caso afirmativo, muestra un mensaje de éxito y redirige a la vista de
	 * login.</li>
	 * </ul>
	 *
	 * @param event Evento de acción disparado al hacer clic en el botón "Enviar".
	 * @throws IOException Si ocurre un error durante la navegación.
	 */
	@FXML
	private void handlerEnviar(ActionEvent event) throws IOException {
		if (existeUsuario) {
			boolean respuesta = alertas.alertaConfirmacion("Confirmación email",
					"Se le enviará su contraseña al email " + lblEmail.getText() + ".\n¿Es corecto?");
			if (respuesta) {
				alertas.alertaInformacion("Email enviado", "Se le ha enviado el Email.\n\nVolviendo al menú.");
				stageManager.switchScene(FxmlView.LOGIN);
			} else {
				alertas.alertaInformacion("Acción cancelada", "Por favor, introduzca un usuario registrado.");
			}

		} else {
			alertas.alertaError("Falta usuario", "Es necesario un usuario registrado para recuperar la contraseña");
		}
	}

	/**
	 * Regresa a la vista de login.
	 *
	 * @param event Evento de acción disparado al hacer clic en "Volver".
	 * @throws IOException Si ocurre un error al cambiar de escena.
	 */
	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.LOGIN);
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
}
