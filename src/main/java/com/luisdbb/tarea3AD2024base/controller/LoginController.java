package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.Alertas;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
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
public class LoginController implements Initializable {

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
	private Label lblFeed;

	@FXML
	private Button btnVolver;

	@FXML
	private Button btnSalir;

	// inyecciones
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private Sesion sesion;

	@Lazy
	@Autowired
	private StageManager stageManager;

	// conf hpVisible
	private boolean isPassVisible = false;
	private Image mostrarIcon;
    private Image ocultarIcon;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// config hpVisible inicial		
        String mostrarPath = resources.getString("contraseñaC.icon");
        String ocultarPath = resources.getString("contraseñaO.icon");

        mostrarIcon = new Image(getClass().getResourceAsStream(mostrarPath));
        ocultarIcon = new Image(getClass().getResourceAsStream(ocultarPath));
        
        hpVisible.setGraphic(createImageView(mostrarIcon));

		// config img btn Login
		String rutaLogin = resources.getString("btnLogin.icon");
		Image imgLogin = new Image(getClass().getResourceAsStream(rutaLogin));
		ImageView viewLogin = new ImageView(imgLogin);
		viewLogin.setFitWidth(60);
		viewLogin.setFitHeight(30);
		btnLogin.setGraphic(viewLogin);

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

		// config imagen usuario
		String rutaUsu = resources.getString("usuario.icon");
		imgUsuario.setImage(new Image(getClass().getResourceAsStream(rutaUsu)));	

		// mnemónicos		
		hpVisible.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.M) {
				hpVisible.fire();
				event.consume();
			}
		});		
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
		btnLogin.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.L) {
				btnLogin.fire();
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
		hpVisible.setTooltip(new Tooltip("Mostrar (Alt+M)"));
		hpContraseña.setTooltip(new Tooltip("Recuperar (Alt+C)"));
		hpRegistro.setTooltip(new Tooltip("Registro (Alt+R)"));
		btnLogin.setTooltip(new Tooltip("Login (Alt+L)"));
		btnVolver.setTooltip(new Tooltip("Volver (Alt+V)"));
		btnSalir.setTooltip(new Tooltip("Salir (Alt+S)"));

	}

	/**
	 * Handler para el botón btnLogin. Método que al pulsarlo realiza las siguientes acciones:
	 * - Comprueba que los campos de usuario y contraseña no estén vacíos o nulos.
	 *   Si alguno de los campos está vacío, muestra un mensaje de alerta indicando que los datos son obligatorios.
	 * - Si los datos están completos, intenta iniciar sesión utilizando el servicio `usuarioService`.
	 * - Dependiendo del perfil del usuario (PEREGRINO, PARADA, ADMINISTRADOR), establece el perfil activo en la sesión
	 *   y cambia la escena correspondiente mediante `stageManager.switchScene`.
	 * - Si el usuario no se encuentra registrado, muestra un mensaje de alerta indicando que los datos no están registrados.
	 * - Si ocurre un error inesperado, muestra un mensaje en el label `lblFeed` indicando un fallo en el inicio de sesión.
	 * 
	 * @param event El evento que dispara el método (clic en el botón).
	 * @throws IOException Si ocurre un error al cambiar la escena o al acceder a recursos de E/S.
	 */

	@FXML
	private void handlerLogin(ActionEvent event) throws IOException {
		lblFeed.setText(" ");
		if (txtUsuario.getText() == null || txtContraseña.getText() == null || txtUsuario.getText().isEmpty()
				|| txtContraseña.getText().isEmpty()) {
			Alertas.alertaError("Faltan datos", "Los campos usuario y contraseña son obligatorios");
		} else {
			Perfil perfilActivo = usuarioService.loguear(txtUsuario.getText(), txtContraseña.getText());
			switch (perfilActivo) {
			case PEREGRINO:
				// sesion
				sesion.setUsuarioActivo(usuarioService.findByUsuario(txtUsuario.getText()));
				sesion.setPerfilActivo(perfilActivo);

				stageManager.switchScene(FxmlView.PEREGRINO);
				break;
			case PARADA:
				// sesion
				sesion.setUsuarioActivo(usuarioService.findByUsuario(txtUsuario.getText()));
				sesion.setPerfilActivo(perfilActivo);

				stageManager.switchScene(FxmlView.PARADA);
				break;
			case ADMINISTRADOR:
				// sesion
				sesion.setUsuarioActivo(usuarioService.findByUsuario(txtUsuario.getText()));
				sesion.setPerfilActivo(perfilActivo);

				stageManager.switchScene(FxmlView.ADMIN);
				break;
			case null:
				Alertas.alertaError("Datos no encontrados", "Los datos introducidos no están registrados.");
				break;
			default:
				lblFeed.getStyleClass().removeAll("lblFeedValido", "lblFeedInvalido");
				lblFeed.getStyleClass().add("lblFeedInvalido");
				lblFeed.setText("Error al hacer login");
			}
		}
	}

	/**
	 * Handler para el botón btnVolver. Método que al pulsarlo vuelve a la ventana principal.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.MAIN);
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
	 * Handler para el Hyperlink hpContraseña. Método que al pulsarlo cambia a la
	 * ventana de recuperación de contraseña.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlerHpContraseña(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.RECUPERACION);
	}

	/**
	 * Handler para el Hyperlink hpRegistro. Método que al pulsarlo cambia a la
	 * ventana de registro de peregrino.
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void handlerHpRegistro(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.REGPEREGRINO);
	}

	@FXML
    private void handlerVisible() {
        isPassVisible = !isPassVisible;
        if (isPassVisible) {
            txtContraseña.setText(passContraseña.getText());
            txtContraseña.setVisible(true);
            passContraseña.setVisible(false);
            hpVisible.setGraphic(createImageView(ocultarIcon)); 
        } else {
            passContraseña.setText(txtContraseña.getText());
            txtContraseña.setVisible(false);
            passContraseña.setVisible(true);
            hpVisible.setGraphic(createImageView(mostrarIcon)); 
        }
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
