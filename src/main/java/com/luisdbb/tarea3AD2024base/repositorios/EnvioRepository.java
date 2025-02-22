package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.luisdbb.tarea3AD2024base.ObjectDB.ObjectDBConnection;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;

@Component
public class EnvioRepository {

	@Autowired
	private ObjectDBConnection oc;

	@Autowired
	private ParadaRepository paradaRepository;	

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
	
	public List<EnvioACasa> getEnvios(long idUsuario) {
		
		long idParada=paradaRepository.findByUsuarioId(idUsuario).getId();
		
		oc.abrirConexion();
		EntityManager em = oc.getEntityManager();

		try {
			TypedQuery<EnvioACasa> query = em.createQuery("SELECT e FROM EnvioACasa e WHERE e.idParada = :idParada", EnvioACasa.class);
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
