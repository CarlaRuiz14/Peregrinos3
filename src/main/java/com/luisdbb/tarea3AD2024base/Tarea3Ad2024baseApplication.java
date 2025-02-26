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
 * Clase principal de la aplicación que combina Spring Boot y JavaFX.
 *
 * Responsabilidades principales:
 * 
 * <ul>
 * <li>Inicializar el contexto de Spring Boot.</li>
 * <li>Configurar la ventana principal de JavaFX.</li>
 * <li>Establecer la escena inicial de la aplicación.</li>
 * <li>Manejar la terminación de la aplicación.</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */

@SpringBootApplication
public class Tarea3Ad2024baseApplication extends Application {

	protected static ConfigurableApplicationContext springContext;
	protected StageManager stageManager;

	/**
	 * Método invocado automáticamente por JavaFX antes de lanzar la aplicación.
	 * 
	 * Inicializa el contexto de Spring Boot y permite que los beans de la
	 * aplicación estén disponibles antes de que la UI de JavaFX se cargue.
	 *
	 * @throws Exception si ocurre algún error durante la inicialización del
	 *                   contexto.
	 */
	@Override
	public void init() throws Exception {
		springContext = springBootApplicationContext();
	}

	/**
	 * Método principal para lanzar la aplicación.
	 * 
	 * @param args Argumentos de la línea de comandos.
	 */
	public static void main(final String[] args) {
		System.setProperty("java.awt.headless", "false");
		Application.launch(args);
	}

	/**
	 * Método invocado automáticamente por JavaFX después de {@code init()}.
	 * 	
	 * Configura la ventana principal de la aplicación, muestra la escena inicial y
	 * carga el icono de la aplicación. También deshabilita el redimensionado de la
	 * ventana y gestiona su cierre adecuado.
	 * 
	 * @param primaryStage El escenario principal proporcionado por JavaFX.
	 * @throws Exception si ocurre un error durante la configuración de la ventana.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		stageManager = springContext.getBean(StageManager.class, primaryStage);

		displayInitialScene();

		ResourceBundle bundle = ResourceBundle.getBundle("Bundle");
		String iconPath = bundle.getString("app.icon");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(iconPath)));

		primaryStage.setResizable(false);

		// Interceptar cierre con la X
		primaryStage.setOnCloseRequest(event -> {
			if (springContext != null && springContext.isActive()) {
				springContext.close();
			}
		});
	}

	/**
	 * Método para configurar y mostrar la escena inicial. Las subclases pueden
	 * sobrescribir este método para personalizarlo.
	 */
	protected void displayInitialScene() {
		stageManager.switchScene(FxmlView.MAIN);
	}

	/**
	 * Inicializa el contexto de Spring Boot.
	 * 
	 * @return Contexto de Spring configurado.
	 */
	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Tarea3Ad2024baseApplication.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}

	/**
	 * Obtiene el contexto de Spring Boot.
	 * 
	 * @return El contexto de Spring Boot en ejecución.
	 */
	public static ConfigurableApplicationContext getSpringContext() {
		return springContext;
	}
}
