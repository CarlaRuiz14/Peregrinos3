package com.luisdbb.tarea3AD2024base;

import java.util.ResourceBundle;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.services.ConjuntoServicioService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación que combina Spring Boot y JavaFX.
 *
 * <p>
 * Responsabilidades principales:
 * </p>
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
	 * Aquí se inicializa el contexto de Spring Boot y, a continuación, se obtiene
	 * el bean {@code ConjuntoServicioRepository} desde el contexto de Spring para
	 * garantizar que la secuencia de IDs (usada por db4o) se cree si no existe.
	 * De esta forma, cuando se inicie la aplicación JavaFX, tendremos disponible
	 * la configuración de persistencia y la secuencia de IDs lista para usar.
	 * <
	 *
	 * @throws Exception si ocurre algún error durante la inicialización
	 */
	@Override
	public void init() throws Exception {
		springContext = springBootApplicationContext();
		
		// en application se pide directamente el bean
	    ConjuntoServicioService css = springContext.getBean(ConjuntoServicioService.class);

	    try {
	    	css.crearSecuenciaId();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
		
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
	 * Configura la ventana principal de la aplicación y muestra la escena inicial. 
	 * También carga el ícono de la aplicación desde los recursos y deshabilita 
	 * el redimensionado de la ventana. 
	 * 
	 * Se intercepta el evento de cierre (la “X”) para cerrar de forma ordenada 
	 * el contexto de Spring y así invocar los métodos marcados con {@code @PreDestroy}.
	 * 
	 *
	 * @param primaryStage El escenario principal proporcionado por JavaFX.
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
	
	 public static ConfigurableApplicationContext getSpringContext() {
	        return springContext;
	    }
}
