package com.luisdbb.tarea3AD2024base.Connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;

@Component
public class ExistDBConnection {

	@Value("${existdb.datasource.driver-class-name}")
	private String driver;

	@Value("${existdb.datasource.url}")
	private String uri;

	@Value("${existdb.datasource.user}")
	private String user;

	@Value("${existdb.datasource.password}")
	private String password;

	private Collection col;

	/**
	 * Abre la conexión con ExistDB si no está abierta.
	 * 
	 * @throws Exception Si ocurre un error al conectar con la base de datos.
	 */
	public void abrirConexion() throws Exception {
		try {
			Class<?> cl = Class.forName(driver);
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			DatabaseManager.registerDatabase(database);

			col = DatabaseManager.getCollection(uri, user, password);
			if (col == null) {
				throw new Exception("No se pudo conectar a la colección base");
			}
			System.out.println("Conexión a eXistDB establecida correctamente.");

		} catch (Exception e) {
			throw new Exception("Error al conectar con ExistDB", e);
		}
	}

	/**
	 * Cierra la conexión con ExistDB si está abierta.
	 * 
	 * @throws Exception Si ocurre un error al cerrar la conexión.
	 */
	public void cerrarConexion() throws Exception {
		if (col != null) {
			try {
				col.close();
			} catch (XMLDBException e) {
				throw new Exception("Error al cerrar la conexión con ExistDB");
			}
		}
	}

	/**
	 * Devuelve la colección de ExistDB actualmente conectada.
	 * 
	 * @return La colección activa o null si no hay conexión.
	 */
	public Collection getCollection() {
		return col;
	}

}