package com.luisdbb.tarea3AD2024base.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.repositorios.EstanciaRepository;

import jakarta.transaction.Transactional;

/**
 * Servicio para la gestión de estancias en paradas.
 *
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class EstanciaService {

	@Autowired
	private EstanciaRepository estanciaRepository;

	@Autowired
	private CarnetService carnetService;

	/**
	 * Obtiene las estancias en una parada dentro de un rango de fechas.
	 * 
	 * @param idParada    Identificador de la parada.
	 * @param fechaInicio Fecha de inicio del rango.
	 * @param fechaFin    Fecha de fin del rango.
	 * @return Lista de estancias en la parada o una lista vacía si no hay
	 *         resultados.
	 */
	public List<Estancia> getEstanciasForParada(Long idParada, LocalDate fechaInicio, LocalDate fechaFin) {
		return estanciaRepository.findByParadaIdAndFechaBetween(idParada, fechaInicio, fechaFin);
	}

	/**
	 * Registra una estancia en una parada para un peregrino.
	 * 
	 * <ul>
	 * <li>Si la estancia es VIP, incrementa el contador de estancias VIP en el
	 * carnet.</li>
	 * <li>Guarda la estancia en la base de datos.</li>
	 * </ul>
	 * 
	 * @param vip       Indica si la estancia es VIP.
	 * @param peregrino Peregrino que realiza la estancia.
	 * @param parada    Parada donde se hospeda el peregrino.
	 * @param carnet    Carnet del peregrino donde se registrará la estancia.
	 * @return La estancia creada o {@code null} si ocurre un error.
	 */
	@Transactional
	public Estancia registrarEstancia(boolean vip, Peregrino peregrino, Parada parada, Carnet carnet) {

		LocalDate hoy = LocalDate.now();

		Estancia estancia = new Estancia(hoy, vip, peregrino, parada);

		if (vip) {
			carnet.setnVips(carnet.getnVips() + 1);
			carnetService.save(carnet);
		}
		estanciaRepository.save(estancia);

		return estancia;
	}

	/**
	 * Obtiene todas las estancias de un peregrino.
	 * 
	 * @param peregrinoId Identificador del peregrino.
	 * @return Lista de estancias del peregrino o una lista vacía si no hay
	 *         registros.
	 */
	public List<Estancia> findByPeregrinoId(Long peregrinoId) {
		return estanciaRepository.findByPeregrinoId(peregrinoId);
	}
}
