package com.luisdbb.tarea3AD2024base.Connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

/**
 * Gestor de conexión a la base de datos db4o
 * 
 * Características principales:
 * <ul>
 * <li>Inicializa y configura la conexión a db4o al iniciar la aplicación.</li>
 * <li>Proporciona un método para obtener la instancia de la base de datos.</li>
 * <li>Cierra la conexión antes de que la aplicación termine.</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @version 1.0
 * @since 28/12/2024
 */
@Component
public class DB4OConnection {

	private ObjectContainer db;

	@Value("${db4o.path}")
	private String path;

	/**
	 * Este método se ejecuta automáticamente al iniciar la aplicación y abre la
	 * conexión a la base de datos si no está ya abierta.
	 */
	@PostConstruct
	public void openConnection() {
		if (db == null || db.ext().isClosed()) {
			EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
			db = Db4oEmbedded.openFile(config, path);
		}
	}

	/**
	 * 
	 * Este método devuelve la instancia actual de la base de datos db4o. Si la
	 * conexión está cerrada, lanza una excepción.
	 * 
	 * @return La instancia de {@code ObjectContainer} que representa la conexión a
	 *         la base de datos.
	 * @throws IllegalStateException Si la conexión no está abierta.
	 */
	public ObjectContainer getDb() {
		if (db == null || db.ext().isClosed()) {
			throw new IllegalStateException("La conexión a db4o no está abierta. Llama a openConnection() primero.");
		}
		return db;
	}

	/**
	 * Este método se ejecuta antes de que la aplicación termine, cerrando la
	 * conexión a la base de datos si aún está abierta.
	 */
	@PreDestroy
	public void closeConnection() {
		if (db != null && !db.ext().isClosed()) {
			db.close();
		}
	}

}