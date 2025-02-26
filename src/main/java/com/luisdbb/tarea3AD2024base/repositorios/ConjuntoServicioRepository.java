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

/**
 * Repositorio para gestionar entidades de {@link ConjuntoContratado} y
 * {@link Servicio} en db4o.
 * 
 * Métodos para ConjuntoContratado:
 * <ul>
 * <li><b>saveConjunto:</b> Guarda un nuevo conjunto en la base de datos.</li>
 * <li><b>findAllConjuntos:</b> Obtiene todos los conjuntos almacenados.</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Component
public class ConjuntoServicioRepository {

	@Autowired
	private DB4OConnection dataConnection;

	@Autowired
	private ParadaRepository paradaRepository;

	/**
	 * Obtiene todas las paradas almacenadas en la base de datos.
	 * 
	 * @return Lista de paradas registradas.
	 */
	public List<Parada> findAllParadas() {
		return paradaRepository.findAll();
	}

	// METODOS CONJUNTOCONTRATADO

	/**
	 * Guarda un conjunto en la base de datos. Si es nuevo, se asigna un ID
	 * incremental.
	 * 
	 * @param conjunto ConjuntoContratado a almacenar.
	 */
	public void saveConjunto(ConjuntoContratado conjunto) {
		ObjectContainer db = dataConnection.getDb();
		try {
			long nuevoId = findAllConjuntos().size() + 1;
			conjunto.setId(nuevoId);
			db.store(conjunto);
			db.commit();
		} catch (Exception e) {
			db.rollback();
			throw new RuntimeException("Error al guardar el conjunto", e);
		}
	}

	/**
	 * Obtiene todos los conjuntos almacenados en la base de datos.
	 * 
	 * @return Conjuntos contratados registrados.
	 */
	public Set<ConjuntoContratado> findAllConjuntos() {
		ObjectContainer db = dataConnection.getDb();

		ConjuntoContratado ejemplo = new ConjuntoContratado();
		ObjectSet<ConjuntoContratado> result = db.queryByExample(ejemplo);

		return new HashSet<>(result);
	}

	// METODOS SERVICIOS

	/**
	 * Comprueba si existe un servicio con el nombre dado, ignorando mayúsculas y
	 * minúsculas.
	 * 
	 * @param nombre Nombre del servicio a verificar.
	 * @return true si el servicio existe, false en caso contrario.
	 */
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

	/**
	 * Guarda un servicio en la base de datos. Si es nuevo, se le asigna un ID
	 * incremental.
	 * 
	 * @param servicio Servicio a almacenar.
	 */
	public void saveServicio(Servicio servicio) {
		ObjectContainer db = dataConnection.getDb();
		try {
			if (servicio.getId() == 0) {
				long nuevoId = findAllServicios().size() + 1;
				servicio.setId(nuevoId);
			}
			db.store(servicio);
			db.commit();
		} catch (Exception e) {
			db.rollback();
			throw new RuntimeException("Error al guardar el servicio", e);
		}
	}

	/**
	 * Obtiene todos los servicios almacenados en la base de datos.
	 * 
	 * @return Conjunto de servicios registrados.
	 */
	public Set<Servicio> findAllServicios() {
		ObjectContainer db = dataConnection.getDb();

		Servicio ejemplo = new Servicio();
		ObjectSet<Servicio> result = db.queryByExample(ejemplo);

		return new HashSet<Servicio>(result);
	}

	/**
	 * Obtiene los servicios asociados a una parada específica según el ID del
	 * usuario.
	 * 
	 * @param idUsuario Identificador del usuario para buscar su parada.
	 * @return Conjunto de servicios disponibles en la parada del usuario.
	 */
	public Set<Servicio> findServiciosByIdParada(long idUsuario) {
		long idParada = paradaRepository.findByUsuarioId(idUsuario).getId();

		Set<Servicio> servicios = new HashSet<>();

		Set<Servicio> todos = findAllServicios();

		for (Servicio s : todos) {
			List<Long> paradas = s.getListaParadas();
			for (long id : paradas) {
				if (id == idParada) {
					servicios.add(s);
				}
			}
		}

		return servicios;
	}

	/**
	 * Busca un servicio por su nombre en la base de datos.
	 * 
	 * @param nombre Nombre del servicio a buscar.
	 * @return Servicio encontrado o null si no existe.
	 */
	public Servicio getServicioByName(String nombre) {
		ObjectContainer db = dataConnection.getDb();

		Servicio ejemplo = new Servicio(0, nombre, 0.0, null, null);
		ObjectSet<Servicio> result = db.queryByExample(ejemplo);

		if (result.hasNext()) {
			return result.next();
		} else {
			return null;
		}
	}
}
