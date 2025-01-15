package com.luisdbb.tarea3AD2024base;

import java.util.ResourceBundle;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@SpringBootApplication
public class Tarea3Ad2024baseApplication extends Application {

	protected ConfigurableApplicationContext springContext;
	protected StageManager stageManager;

	// INICIALIZA el contexto de Spring
	@Override
	public void init() throws Exception {
		springContext = springBootApplicationContext();
	}

	// lanza la app 
	public static void main(final String[] args) {
		Application.launch(args);
		//System.setProperty("java.awt.headless", "true");

	}

	// configura la ventana principal
	// llamado automaticamente despues de init
	@Override
	public void start(Stage primaryStage) throws Exception {
		// obtiene una instancia del stage desde el contexto de Spring y lo pasa a su
		// constructor
		stageManager = springContext.getBean(StageManager.class, primaryStage);
		// metodo
		displayInitialScene();

		// configuración de icono del Stage
		ResourceBundle bundle = ResourceBundle.getBundle("Bundle");
		String iconPath = bundle.getString("app.icon");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(iconPath)));

		primaryStage.setResizable(false);

	}

	/**
	 * Útil para que las subclases sobrescriban este método si desean cambiar la
	 * primera escena que se muestra al iniciar.
	 */
	protected void displayInitialScene() {
		stageManager.switchScene(FxmlView.MAIN);
	}

	// CONFIGURA el contexto de Spring
	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Tarea3Ad2024baseApplication.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}

}
