package com.luisdbb.tarea3AD2024base.repositorios;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luisdbb.tarea3AD2024base.ObjectDB.ObjectDBConnection;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;

@Component
public class EnvioRepository {

	@Autowired
	private ObjectDBConnection objectDBConnection;

	public EnvioACasa saveEnvio(EnvioACasa envio) {

		EntityManager em = objectDBConnection.getEntityManager();
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
		}

	}

}
