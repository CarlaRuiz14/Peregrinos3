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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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

		// config img btn flecha
		botones.imgFlecha(btnEnviar);

		// config img btn Volver
		botones.imgVolver(btnVolver);

		// config img btn Salir
		botones.imgSalir(btnSalir);

		// config img info
		ayuda.configImgInfo(hpInfo);

		// mnemónicos
		mnemonicConfig.infoMnemonic(hpInfo);

		btnEnviar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
				btnEnviar.fire();
				event.consume();
			}
		});

		mnemonicConfig.volverMnemonic(btnVolver);


		mnemonicConfig.salirMnemonic(btnSalir);


		// tooltips
		tooltipConfig.salirTooltip(btnSalir);
		btnEnviar.setTooltip(new Tooltip("Enviar (Alt+Enter)"));
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);

	}

	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		ayuda.configInfo("/help/recContraseña.html");
	}

	@FXML
	private void handlerEnviar(ActionEvent event) throws IOException {
		if (existeUsuario) {
			boolean respuesta = alertas.alertaConfirmacion("Confirmación email",
					"Se le enviará su contraseña al email "+ lblEmail.getText() +".\n¿Es corecto?");
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

	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}
}
