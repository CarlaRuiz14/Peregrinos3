package com.luisdbb.tarea3AD2024base.ObjectDB;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

/**
 * Clase que gestiona la conexión con la base de datos ObjectDB.
 * 
 * Proporciona métodos para abrir y cerrar la conexión, así como para obtener un
 * `EntityManager` que permite interactuar con la base de datos.
 */
@Component
public class ObjectDBConnection {

	private static final String objectdbPath = "objectdb:ODBB_Peregrinos.odb";
	private EntityManagerFactory emf;

	/**
	 * Abre la conexión con ObjectDB si no está abierta.
	 * 
	 * <ul>
	 * <li>Si la conexión no está abierta, se crea una nueva instancia de
	 * `EntityManagerFactory`.</li>
	 * <li>Si la conexión ya está abierta, no se realiza ninguna acción.</li>
	 * </ul>
	 */
	public void abrirConexion() {
		if (emf == null || !emf.isOpen()) {
			emf = Persistence.createEntityManagerFactory(objectdbPath);
		}
	}

	/**
	 * Obtiene `EntityManager` para realizar operaciones en la base de datos.
	 * 
	 * @return Un `EntityManager` para interactuar con la base de datos.
	 */
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	/**
	 * Cierra la conexión con ObjectDB si está abierta.
	 * 
	 * <ul>
	 * <li>Si la conexión está abierta, se cierra el `EntityManagerFactory`.</li>
	 * <li>Si la conexión ya está cerrada, no se realiza ninguna acción.</li>
	 * </ul>
	 */
	public void cerrarConexion() {
		if (emf != null && emf.isOpen()) {
			emf.close();
		}
	}
}