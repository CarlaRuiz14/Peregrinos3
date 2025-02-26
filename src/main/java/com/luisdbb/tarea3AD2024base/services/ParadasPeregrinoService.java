package com.luisdbb.tarea3AD2024base.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrino;
import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrinoId;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.repositorios.ParadasPeregrinoRepository;

import jakarta.transaction.Transactional;

/**
 * Servicio para gestionar la relación entre peregrinos y paradas.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class ParadasPeregrinoService {

	private static final double distanciaParadas = 15.0;
	private static LocalDate hoy = LocalDate.now();

	@Autowired
	private ParadasPeregrinoRepository paradasPeregrinoRepository;

	@Autowired
	private CarnetService carnetService;

	@Autowired
	private PeregrinoService peregrinoService;

	/**
	 * Registra la parada inicial de un peregrino.
	 * 
	 * @param nombreUsuario Nombre de usuario del peregrino.
	 * @param parada        Parada donde inicia su camino.
	 */
	@Transactional
	public void registrarParadaInicial(String nombreUsuario, Parada parada) {
		Peregrino peregrino = peregrinoService.findByNameUsuario(nombreUsuario);
		ParadasPeregrino paradaPeregrino = new ParadasPeregrino(peregrino, parada, hoy);
		paradasPeregrinoRepository.save(paradaPeregrino);
	}

	/**
	 * Comprueba si un peregrino ha visitado una parada específica en la fecha
	 * actual.
	 * 
	 * @param parada    Parada a comprobar.
	 * @param peregrino Peregrino que se desea verificar.
	 * @return {@code true} si el peregrino ha visitado la parada, {@code false} en
	 *         caso contrario.
	 */
	public boolean existeParada(Parada parada, Peregrino peregrino) {
		ParadasPeregrinoId id = new ParadasPeregrinoId(peregrino.getId(), parada.getId(), LocalDate.now());
		return paradasPeregrinoRepository.existsById(id);
	}

	/**
	 * Registra una parada visitada por un peregrino y sella su carnet aumentando la
	 * distancia recorrida.
	 * 
	 * @param carnet    Carnet del peregrino.
	 * @param parada    Parada visitada.
	 * @param peregrino Peregrino que realizó la visita.
	 */
	@Transactional
	public void registrarParadaYSellarCarnet(Carnet carnet, Parada parada, Peregrino peregrino) {

		carnet.setDistancia(carnet.getDistancia() + distanciaParadas);
		carnetService.save(carnet);

		ParadasPeregrino paradaPeregrino = new ParadasPeregrino(peregrino, parada, hoy);

		paradasPeregrinoRepository.save(paradaPeregrino);
	}

	/**
	 * Obtiene la lista de paradas visitadas por un peregrino.
	 * 
	 * @param idPeregrino ID del peregrino.
	 * @return Lista de paradas visitadas o una lista vacía si no hay registros.
	 */
	public List<ParadasPeregrino> listarParadasPeregrino(long idPeregrino) {
		return paradasPeregrinoRepository.findByIdIdPeregrino(idPeregrino);
	}

}
