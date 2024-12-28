package com.luisdbb.tarea3AD2024base.config;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Cargará FXML en el método load y registrará a Spring como la fábrica de
 * controladores FXML. Permite que Spring y JavaFX coexistan una vez que el
 * contexto de la aplicación de Spring ha sido inicializado.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Component
public class SpringFXMLLoader {

	private final ResourceBundle resourceBundle;

	// contexto Spring contiene todos los beans y permite acceder a ellos
	private final ApplicationContext context;

	// automaticamente este constructor
	@Autowired
	public SpringFXMLLoader(ApplicationContext context, ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
		this.context = context;
	}

	public Parent load(String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setControllerFactory(context::getBean); // Spring now FXML Controller Factory
		loader.setResources(resourceBundle);
		loader.setLocation(getClass().getResource(fxmlPath));
		return loader.load();
	}
}
