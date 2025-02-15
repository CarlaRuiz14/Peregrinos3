package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;
import com.luisdbb.tarea3AD2024base.config.DataConnection;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.SecuenciaId;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;

@Repository
public class ConjuntoServicioRepository {

	@Autowired
	private DataConnection dataConnection;

	@Autowired
	private ParadaRepository paradaRepository;

	@Autowired
	private SecuenciaId secuenciaId;

	public List<Parada> findAllParadas() {
		return paradaRepository.findAll();
	}

	// METODOS CONJUNTOCONTRATADO

	// METODOS CONJUNTOSERVICIOS

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

	// quitar validacion de nombre cuando este implementado el lblfeed
	public void saveServicio(Servicio servicio) {
		ObjectContainer db = dataConnection.getDb();

		long nuevoId = secuenciaId.nextId();
		servicio.setId(nuevoId);
		actualizarSecuenciaId(nuevoId);
		db.store(servicio);
		db.commit();

	}

	public List<Servicio> findAllServicios() {
		ObjectContainer db = dataConnection.getDb();

		Servicio ejemplo = new Servicio();
		ObjectSet<Servicio> result = db.queryByExample(ejemplo);

		return new ArrayList<>(result);
	}

	// METODOS SECUENCIAID
	public void crearSecuenciaId() {
		ObjectContainer db = dataConnection.getDb();

		SecuenciaId ejemplo = new SecuenciaId();
		ObjectSet<SecuenciaId> result = db.queryByExample(ejemplo);

		if (result.isEmpty()) {
			SecuenciaId nueva = new SecuenciaId(0);
			db.store(nueva);
			db.commit();
		}
	}

	public void actualizarSecuenciaId(long idNuevo) {
		ObjectContainer db = dataConnection.getDb();
		SecuenciaId ejemplo = new SecuenciaId();
		ObjectSet<SecuenciaId> result = db.queryByExample(ejemplo);

		if (result.hasNext()) {
			SecuenciaId secuencia = result.next();
			secuencia.setId(idNuevo);
			db.store(secuencia);
			db.commit();
		}
	}
}
