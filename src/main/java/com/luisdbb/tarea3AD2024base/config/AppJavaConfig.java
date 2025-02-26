
package com.luisdbb.tarea3AD2024base.config;

import java.io.IOException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javafx.stage.Stage;

/**
 * Clase de configuración principal de la aplicación.
 * 
 * <ul>
 * <li>Proporciona configuraciones para el manejo del contexto de Spring.</li>
 * <li>Define Beans esenciales como el <b>ResourceBundle</b> y el
 * <b>StageManager</b>.</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Configuration
public class AppJavaConfig {

	@Autowired
	SpringFXMLLoader springFXMLLoader;

	@Bean
	public ResourceBundle resourceBundle() {
		return ResourceBundle.getBundle("Bundle");
	}

	@Bean
	@Lazy(value = true)
	public StageManager stageManager(Stage stage) throws IOException {
		return new StageManager(springFXMLLoader, stage);
	}
}
