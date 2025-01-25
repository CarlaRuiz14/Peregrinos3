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

/**
 * @author Carla Ruiz
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// config hpVisible inicial
		String mostrarPath = resources.getString("contraseñaC.icon");
		String ocultarPath = resources.getString("contraseñaO.icon");

		mostrarIcon = new Image(getClass().getResourceAsStream(mostrarPath));
		ocultarIcon = new Image(getClass().getResourceAsStream(ocultarPath));

		hpVisible.setGraphic(botones.createImageView(mostrarIcon));
		
		// config img info
				ayuda.configImgInfo(hpInfo);

		// config img btn Login
		botones.imgFlecha(btnLogin);

		// config img btn Volver
		botones.imgVolver(btnVolver);

		// config img btn Salir
		botones.imgSalir(btnSalir);

		// config imagen usuario
		String rutaUsu = resources.getString("usuario.icon");
		imgUsuario.setImage(new Image(getClass().getResourceAsStream(rutaUsu)));

		// mnemónicos		
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


		// tooltips
		tooltipConfig.infoTooltip(hpInfo);
		tooltipConfig.visibleTooltip(hpVisible);
		hpContraseña.setTooltip(new Tooltip("Recuperar (Alt+C)"));
		hpRegistro.setTooltip(new Tooltip("Registro (Alt+R)"));
		btnLogin.setTooltip(new Tooltip("Login (Alt+L)"));
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);

	}

	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		ayuda.configInfo("/help/help.html");
	}
	
	@FXML
	private void handlerLogin(ActionEvent event) throws IOException {		

		String contraseña = passContraseña.isVisible() ? passContraseña.getText() : txtContraseña.getText();
	
		int check=validaciones.validarCredenciales(txtUsuario.getText(), contraseña);
		if(check!=0) {
			switch(check) {
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

	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.MAIN);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}

	@FXML
	private void handlerHpContraseña(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.RECUPERACION);
	}

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
			hpVisible.setGraphic(botones.createImageView(ocultarIcon));
		} else {
			passContraseña.setText(txtContraseña.getText());
			txtContraseña.setVisible(false);
			passContraseña.setVisible(true);
			hpVisible.setGraphic(botones.createImageView(mostrarIcon));
		}
	}


}
