package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luisdbb.tarea3AD2024base.Connection.ObjectDBConnection;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

/**
 * Repositorio para la gestión de envíos a casa mediante ObjectDB. Proporciona
 * métodos para almacenar y recuperar envíos asociados a una parada.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Component
public class EnvioRepository {

	@Autowired
	private ObjectDBConnection oc;

	@Autowired
	private ParadaRepository paradaRepository;

	/**
	 * Guarda un nuevo envío en la base de datos.
	 * 
	 * @param envio Envío a almacenar.
	 * @return El envío almacenado o {@code null} si ocurre un error.
	 */
	public EnvioACasa saveEnvio(EnvioACasa envio) {

		oc.abrirConexion();
		EntityManager em = oc.getEntityManager();

		try {
			em.getTransaction().begin();
			em.persist(envio);
			em.getTransaction().commit();
			return envio;
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return null;
		} finally {
			em.close();
			oc.cerrarConexion();
		}
	}

	/**
	 * Obtiene todos los envíos registrados en la parada del usuario.
	 * 
	 * @param idUsuario Identificador del usuario.
	 * @return Lista de envíos asociados a la parada del usuario.
	 */
	public List<EnvioACasa> getEnvios(long idUsuario) {

		long idParada = paradaRepository.findByUsuarioId(idUsuario).getId();

		oc.abrirConexion();
		EntityManager em = oc.getEntityManager();

		try {
			TypedQuery<EnvioACasa> query = em.createQuery("SELECT e FROM EnvioACasa e WHERE e.idParada = :idParada",
					EnvioACasa.class);
			query.setParameter("idParada", idParada);
			return query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			em.close();
			oc.cerrarConexion();
		}

	}

}
