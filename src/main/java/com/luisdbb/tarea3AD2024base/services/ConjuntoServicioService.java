package com.luisdbb.tarea3AD2024base.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.repositorios.ConjuntoServicioRepository;

/**
 * Servicio para la gestión de conjuntos contratados y servicios.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class ConjuntoServicioService {

	@Autowired
	private ConjuntoServicioRepository csr;

	// METODOS CONJUNTOCONTRATADO

	/**
	 * Guarda un conjunto contratado en la base de datos.
	 * 
	 * @param conjuntoNuevo Conjunto a guardar o {@code null} si no lo guarda.
	 * @throws RuntimeException si ocurre un error en la operación.
	 */
	public void saveConjunto(ConjuntoContratado conjuntoNuevo) {
		try {
			csr.saveConjunto(conjuntoNuevo);
		} catch (Exception e) {
			throw new RuntimeException("Error al guardar el conjunto", e);
		}
	}

	// METODOS SERVICIOS

	/**
	 * Verifica si un servicio con el nombre especificado ya existe.
	 * 
	 * @param nombre Nombre del servicio.
	 * @return {@code true} si el servicio existe, {@code false} en caso contrario.
	 */
	public boolean existeNombreServicio(String nombre) {
		return csr.existeNombreServicio(nombre);
	}

	/**
	 * Guarda un servicio en la base de datos.
	 * 
	 * @param servicioNuevo Servicio a guardar.
	 * @throws RuntimeException si ocurre un error en la operación.
	 */
	public void saveServicio(Servicio servicioNuevo) {
		try {
			csr.saveServicio(servicioNuevo);
		} catch (Exception e) {
			throw new RuntimeException("Error al guardar el servicio", e);
		}
	}

	/**
	 * Obtiene todos los servicios almacenados en la base de datos.
	 * 
	 * @return Conjunto de servicios disponibles.
	 */
	public Set<Servicio> findAllServicios() {
		return csr.findAllServicios();
	}

	/**
	 * Crea un objeto {@link Servicio} vacío con valores predeterminados.
	 * 
	 * @return Nuevo servicio sin datos inicializados.
	 */
	public Servicio crearServicioVacio() {
		return new Servicio(0, "", 0.0, null, null);
	}

	/**
	 * Obtiene los servicios asociados a una parada específica.
	 * 
	 * @param idParada Identificador de la parada.
	 * @return Conjunto de servicios disponibles en la parada.
	 */
	public Set<Servicio> findServiciosByIdParada(long idParada) {
		return csr.findServiciosByIdParada(idParada);
	}

	/**
	 * Busca un servicio por su nombre.
	 * 
	 * @param nombre Nombre del servicio.
	 * @return Servicio encontrado o {@code null} si no existe.
	 */
	public Servicio getServicioByName(String nombre) {
		return csr.getServicioByName(nombre);
	}

}
