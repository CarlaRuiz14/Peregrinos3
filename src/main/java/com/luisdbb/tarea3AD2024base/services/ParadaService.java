package com.luisdbb.tarea3AD2024base.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrino;
import com.luisdbb.tarea3AD2024base.repositorios.ParadaRepository;

/**
 * Servicio para la gestión de paradas en la aplicación.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class ParadaService {

	@Autowired
	private ParadaRepository paradaRepository;

	@Autowired
	private ParadasPeregrinoService paradasPeregrinoService;

	/**
	 * Guarda una parada en la base de datos.
	 * 
	 * @param entidad Parada a guardar.
	 * @return Parada guardada.
	 */
	public Parada save(Parada entidad) {
		return paradaRepository.save(entidad);
	}

	/**
	 * Comprueba si una parada con un nombre y una región específicos existe.
	 * 
	 * @param nombre Nombre de la parada.
	 * @param region Región de la parada.
	 * @return {@code true} si la parada existe, {@code false} en caso contrario.
	 */
	public boolean existeParada(String nombre, char region) {

		Parada parada = findByNombre(nombre);
		if (parada == null) {
			return false;
		}

		return Character.toLowerCase(parada.getRegion()) == Character.toLowerCase(region);
	}

	/**
	 * Busca una parada por su nombre.
	 * 
	 * @param parada Nombre de la parada a buscar.
	 * @return Parada encontrada o {@code null} si no existe.
	 */
	public Parada findByNombre(String parada) {
		return paradaRepository.findByNombre(parada);
	}

	/**
	 * Busca una parada por su ID.
	 * 
	 * @param id Identificador único de la parada.
	 * @return Parada encontrada.
	 * @throws RuntimeException si la parada no existe.
	 */
	public Parada findById(Long id) {
		return paradaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No se encontró la parada con ID: " + id));
	}

	/**
	 * Busca una parada asociada a un usuario.
	 * 
	 * @param id ID del usuario.
	 * @return Parada asociada al usuario o {@code null} si no existe.
	 */
	public Parada findByUsuario(Long id) {
		return paradaRepository.findByUsuarioId(id);
	}

	/**
	 * Recupera todas las paradas almacenadas en la base de datos.
	 * 
	 * @return Lista de todas las paradas.
	 */
	public List<Parada> findAll() {
		return paradaRepository.findAll();
	}

	/**
	 * Obtiene la lista de paradas visitadas por un peregrino.
	 * 
	 * @param peregrinoId ID del peregrino.
	 * @return Lista de paradas visitadas por el peregrino.
	 */
	public List<Parada> obtenerParadasPorPeregrino(Long peregrinoId) {
		List<ParadasPeregrino> listaP = paradasPeregrinoService.listarParadasPeregrino(peregrinoId);
		List<Parada> paradas = new ArrayList<>();

		for (ParadasPeregrino p : listaP) {
			Parada parada = paradaRepository.findParadaById(p.getParada().getId());
			paradas.add(parada);
		}

		return paradas;
	}
}
