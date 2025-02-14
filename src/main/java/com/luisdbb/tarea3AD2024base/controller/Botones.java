package com.luisdbb.tarea3AD2024base.controller;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import com.luisdbb.tarea3AD2024base.Tarea3Ad2024baseApplication;
import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Clase para configurar iconos de botones en la interfaz gráfica.
 * 
 * <b>Funciones principales:</b>
 * <ul>
 * <li>Asignar imágenes a botones según su función.</li>
 * <li>Crear objetos <code>ImageView</code> reutilizables con un tamaño
 * uniforme.</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Component
public class Botones {

	@Autowired
	ResourceBundle resources;

	@Autowired
	private Alertas alertas;	

	
	public void imgFlecha(Button button) {
		String rutaFlecha = resources.getString("btnFlecha.icon");
		Image imgFlecha = new Image(getClass().getResourceAsStream(rutaFlecha));
		ImageView viewFlecha = new ImageView(imgFlecha);
		viewFlecha.setFitWidth(80);
		viewFlecha.setFitHeight(40);
		button.setGraphic(viewFlecha);
	}

	public void imgSalir(Button button) {
		String rutaSalir = resources.getString("btnSalir.icon");
		Image imgSalir = new Image(getClass().getResourceAsStream(rutaSalir));
		ImageView viewSalir = new ImageView(imgSalir);
		viewSalir.setFitWidth(30);
		viewSalir.setFitHeight(30);
		button.setGraphic(viewSalir);
	}

	public void imgLogout(Button button) {
		String rutaLog = resources.getString("btnLogout.icon");
		Image imgLogout = new Image(getClass().getResourceAsStream(rutaLog));
		ImageView viewLog = new ImageView(imgLogout);
		viewLog.setFitWidth(30);
		viewLog.setFitHeight(30);
		button.setGraphic(viewLog);
	}

	public void imgVolver(Button button) {
		String rutaVolver = resources.getString("btnVolver.icon");
		Image imgVolver = new Image(getClass().getResourceAsStream(rutaVolver));
		ImageView viewVolver = new ImageView(imgVolver);
		viewVolver.setFitWidth(30);
		viewVolver.setFitHeight(30);
		button.setGraphic(viewVolver);
	}

	public void imgLimpiar(Button button) {
		String rutaLimp = resources.getString("btnLimpiar.icon");
		Image imgLimp = new Image(getClass().getResourceAsStream(rutaLimp));
		ImageView viewLimp = new ImageView(imgLimp);
		viewLimp.setFitWidth(30);
		viewLimp.setFitHeight(30);
		button.setGraphic(viewLimp);
	}

	public ImageView createImageView(Image image) {
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(40);
		imageView.setFitHeight(40);
		imageView.setPreserveRatio(true);
		return imageView;
	}	

	
	public void salirConfig() {
		boolean respuesta=alertas.alertaConfirmacion("Salir", "Va a cerrar la apliación, ¿está seguro?");		
		if(respuesta) {
			alertas.alertaInformacion("Salir", "Saliendo de la apliación.");
			
	        ConfigurableApplicationContext springContext = Tarea3Ad2024baseApplication.getSpringContext();

			if (springContext != null && springContext.isActive()) {
		        springContext.close();
		    }
			Platform.exit();
		}else {
			alertas.alertaInformacion("Salir", "Acción cancelada. \nVolviendo.");
		}		
	}
	
	public void logoutConfig(UsuarioService usuario, StageManager stage) {		
		boolean respuesta=alertas.alertaConfirmacion("Cerrar sesión", "¿Está seguro de que desea cerrar su sesión?");		
		if(respuesta) {
			alertas.alertaInformacion("Sesión cerrada", "Ha cerrado su sesión correctamente.");
			usuario.configurarSesion(null, Perfil.INVITADO);
			stage.switchScene(FxmlView.LOGIN);
		}else {
			alertas.alertaInformacion("Operación cancelada", "Su sesión permanece activa.\nVolviendo.");
		}		
	}
}
