package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.luisdbb.tarea3AD2024base.config.DB4OConnection;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;

@Component
public class ConjuntoServicioRepository {

	@Autowired
	private DB4OConnection dataConnection;

	@Autowired
	private ParadaRepository paradaRepository;	

	public List<Parada> findAllParadas() {
		return paradaRepository.findAll();
	}

	// METODOS CONJUNTOCONTRATADO

	public void saveConjunto(ConjuntoContratado conjunto) {
		ObjectContainer db = dataConnection.getDb();
		try {
			long nuevoId = findAllConjuntos().size()+1;
			conjunto.setId(nuevoId);
			db.store(conjunto);
			db.commit();
		} catch (Exception e) {
			db.rollback();
			throw new RuntimeException("Error al guardar el conjunto", e);
		}
	}
	
	public Set<ConjuntoContratado> findAllConjuntos() {
	    ObjectContainer db = dataConnection.getDb();

	    ConjuntoContratado ejemplo = new ConjuntoContratado();
	    ObjectSet<ConjuntoContratado> result = db.queryByExample(ejemplo);
	   
	    return new HashSet<>(result);
	}


	// METODOS SERVICIOS

	public boolean existeNombreServicio(String nombre) {
		ObjectContainer db = dataConnection.getDb();

		// Native Query (Predicate) para filtrar por nombre ignorando may/min
		List<Servicio> encontrados = db.query(new Predicate<Servicio>() {
			@Override
			public boolean match(Servicio s) {
				return s.getNombre().equalsIgnoreCase(nombre);
			}
		});

		return !encontrados.isEmpty();

	}

	public void saveServicio(Servicio servicio) {
		ObjectContainer db = dataConnection.getDb();
		try {	
			if(servicio.getId()==0) {
				long nuevoId = findAllServicios().size()+1;
				servicio.setId(nuevoId);				
			}			
			db.store(servicio);
			db.commit();
		} catch (Exception e) {
			db.rollback();
			throw new RuntimeException("Error al guardar el servicio", e);
		}
	}

	
	public Set<Servicio> findAllServicios() {
	    ObjectContainer db = dataConnection.getDb();

	    Servicio ejemplo = new Servicio();
	    ObjectSet<Servicio> result = db.queryByExample(ejemplo);
	   
	    return new HashSet<Servicio>(result);
	}

	
	public Set<Servicio> findServiciosByIdParada(long idUsuario){
		
		long idParada=paradaRepository.findByUsuarioId(idUsuario).getId();
		
		Set<Servicio> servicios = new HashSet<>();
		
		Set<Servicio> todos =findAllServicios();
		
		for(Servicio s:todos) {
			List<Long> paradas = s.getListaParadas();
			for(long id:paradas) {				
				if(id==idParada) {
					servicios.add(s);
				}
			}			
		}
		
		return servicios;
		
	}
	
	
	public Servicio getServicioByName(String nombre) {
	    ObjectContainer db = dataConnection.getDb();

	    Servicio ejemplo = new Servicio(0,nombre,0.0,null,null);
	    ObjectSet<Servicio> result = db.queryByExample(ejemplo);
	    
	    if(result.hasNext()) {	    	
	    	return result.next();
	    }else {
	    	return null;
	    }
		
		
	}
	
	
	
}
