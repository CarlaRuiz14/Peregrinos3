package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;
import com.luisdbb.tarea3AD2024base.services.Validaciones;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * LoginController es el controlador para la pantalla de inicio de sesión.
 *
 * @author Carla
 * @since 28/12/2024
 */
@Controller
public class LoginController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private ImageView imgUsuario;

	@FXML
	private TextField txtUsuario;

	@FXML
	private Hyperlink hpVisible;

	@FXML
	private TextField txtContraseña;

	@FXML
	private PasswordField passContraseña;

	@FXML
	private Hyperlink hpContraseña;

	@FXML
	private Hyperlink hpRegistro;

	@FXML
	private Button btnLogin;

	@FXML
	private Button btnVolver;

	@FXML
	private Button btnSalir;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private Ayuda ayuda;

	@Autowired
	private Alertas alertas;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private Validaciones validaciones;

	@Autowired
	private Botones botones;

	@Autowired
	private Mnemonic mnemonicConfig;

	@Autowired
	private Tooltips tooltipConfig;

	private boolean isPassVisible = false;
	private Image mostrarIcon;
	private Image ocultarIcon;

	/**
	 * Inicializa el controlador con la configuración de iconos, atajos de teclado y
	 * tooltips.
	 * <ul>
	 * <li>Carga los iconos para mostrar y ocultar la contraseña.</li>
	 * <li>Configura la visibilidad del campo de contraseña.</li>
	 * <li>Establece los tooltips y atajos para los elementos de la interfaz.</li>
	 * </ul>
	 *
	 * @param location  La URL utilizada para resolver el objeto raíz o null.
	 * @param resources Los recursos de localización o null.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		String mostrarPath = resources.getString("contraseñaC.icon");
		String ocultarPath = resources.getString("contraseñaO.icon");

		mostrarIcon = new Image(getClass().getResourceAsStream(mostrarPath));
		ocultarIcon = new Image(getClass().getResourceAsStream(ocultarPath));

		hpVisible.setGraphic(botones.createImageView(mostrarIcon));

		ayuda.configImgInfo(hpInfo);
		botones.imgFlecha(btnLogin);
		botones.imgVolver(btnVolver);
		botones.imgSalir(btnSalir);

		String rutaUsu = resources.getString("usuario.icon");
		imgUsuario.setImage(new Image(getClass().getResourceAsStream(rutaUsu)));

		mnemonicConfig.infoMnemonic(hpInfo);
		mnemonicConfig.visibleMnemonic(hpVisible);
		hpContraseña.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.C) {
				hpContraseña.fire();
				event.consume();
			}
		});
		hpRegistro.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.R) {
				hpRegistro.fire();
				event.consume();
			}
		});
		mnemonicConfig.volverMnemonic(btnVolver);
		mnemonicConfig.salirMnemonic(btnSalir);

		tooltipConfig.infoTooltip(hpInfo);
		tooltipConfig.visibleTooltip(hpVisible);
		hpContraseña.setTooltip(new Tooltip("Recuperar (Alt+C)"));
		hpRegistro.setTooltip(new Tooltip("Registro (Alt+R)"));
		btnLogin.setTooltip(new Tooltip("Login (Alt+L)"));
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);
	}

	/**
	 * Muestra la ayuda del login en una ventana modal.
	 * 
	 * <ul>
	 * <li>Abre el archivo de ayuda correspondiente a la pantalla de login.</li>
	 * </ul>
	 *
	 * @param event Evento de acción disparado al hacer clic en el enlace de ayuda.
	 * @throws IOException Si ocurre un error al cargar el recurso de ayuda.
	 */
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/login.html", stage);
	}

	/**
	 * Procesa el inicio de sesión del usuario.
	 * <ul>
	 * <li>Obtiene el nombre de usuario y contraseña, considerando la visibilidad
	 * del campo de contraseña.</li>
	 * <li>Valida que los campos no estén vacíos mediante el servicio de
	 * validaciones.</li>
	 * <li>Realiza el logueo y, según el perfil, redirige a la vista
	 * correspondiente.</li>
	 * </ul>
	 *
	 * @param event Evento de acción disparado al hacer clic en el botón de login.
	 * @throws IOException Si ocurre un error durante el inicio de sesión.
	 */
	@FXML
	private void handlerLogin(ActionEvent event) throws IOException {

		String contraseña = passContraseña.isVisible() ? passContraseña.getText() : txtContraseña.getText();

		int check = validaciones.validarCredenciales(txtUsuario.getText(), contraseña);
		if (check != 0) {
			switch (check) {
			case 1:
				alertas.alertaError("Faltan datos", "El campo usuario es obligatorio.");
				return;
			case 2:
				alertas.alertaError("Faltan datos", "El campo contraseña es obligatorio.");
				return;
			}
		}

		Perfil perfilActivo = usuarioService.loguear(txtUsuario.getText(), contraseña);

		switch (perfilActivo) {
		case PEREGRINO:
			usuarioService.configurarSesion(txtUsuario.getText(), perfilActivo);
			stageManager.switchScene(FxmlView.PEREGRINO);
			break;
		case PARADA:
			usuarioService.configurarSesion(txtUsuario.getText(), perfilActivo);
			stageManager.switchScene(FxmlView.PARADA);
			break;
		case ADMINISTRADOR:
			usuarioService.configurarSesion(txtUsuario.getText(), perfilActivo);
			stageManager.switchScene(FxmlView.ADMIN);
			break;
		case null:
			alertas.alertaError("Datos no encontrados", "Los datos introducidos no están registrados.");
			break;
		default:
			alertas.alertaError("Error", "No se ha podido iniciar sesión.");
		}
	}

	/**
	 * Regresa a la vista principal.
	 * 
	 * @param event Evento de acción disparado al hacer clic en el botón "Volver".
	 * @throws IOException Si ocurre un error al cambiar de escena.
	 */
	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.MAIN);
	}

	/**
	 * Ejecuta la acción de salida de la aplicación.
	 *
	 * @param event Evento de acción disparado al hacer clic en el botón "Salir".
	 * @throws IOException Si ocurre un error durante la salida.
	 */
	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}

	/**
	 * Navega a la vista de recuperación de contraseña.
	 * 
	 * <ul>
	 * <li>Cambia la escena a la vista de recuperación.</li>
	 * </ul>
	 *
	 * @param event Evento de acción disparado al hacer clic en el enlace de
	 *              recuperación.
	 * @throws IOException Si ocurre un error durante la navegación.
	 */
	@FXML
	private void handlerHpContraseña(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.RECUPERACION);
	}

	/**
	 * Navega a la vista de registro de usuario.
	 * 
	 * <ul>
	 * <li>Cambia la escena a la vista de registro para nuevos peregrinos.</li>
	 * </ul>
	 *
	 * @param event Evento de acción disparado al hacer clic en el enlace de
	 *              registro.
	 * @throws IOException Si ocurre un error durante la navegación.
	 */
	@FXML
	private void handlerHpRegistro(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.REGPEREGRINO);
	}

	/**
	 * Alterna la visibilidad del campo de contraseña.
	 * <ul>
	 * <li>Si la contraseña está oculta, la muestra en un campo de texto y cambia el
	 * icono a "ocultar".</li>
	 * <li>Si la contraseña es visible, la oculta y cambia el icono a
	 * "mostrar".</li>
	 * </ul>
	 */
	@FXML
	private void handlerVisible() {
		isPassVisible = !isPassVisible;
		if (isPassVisible) {
			txtContraseña.setText(passContraseña.getText());
			txtContraseña.setVisible(true);
			passContraseña.setVisible(false);
			hpVisible.setGraphic(botones.createImageView(ocultarIcon));
		} else {
			passContraseña.setText(txtContraseña.getText());
			txtContraseña.setVisible(false);
			passContraseña.setVisible(true);
			hpVisible.setGraphic(botones.createImageView(mostrarIcon));
		}
	}
}
