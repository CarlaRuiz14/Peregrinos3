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
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class EstanciaService {

	@Autowired
	private EstanciaRepository estanciaRepository;

	@Autowired
	private CarnetService carnetService;

	public List<Estancia> getEstanciasForParada(Long idParada, LocalDate fechaInicio, LocalDate fechaFin) {
		return estanciaRepository.findByIdParadaAndFechaBetween(idParada, fechaInicio, fechaFin);
	}

	@Transactional
	public boolean registrarEstancia(boolean vip, Peregrino peregrino, Parada parada, Carnet carnet) {

		LocalDate hoy = LocalDate.now();

		Estancia estancia = new Estancia(hoy, vip, peregrino, parada);

		if (vip) {
			carnet.setnVips(carnet.getnVips() + 1);
			carnetService.save(carnet);
		}

		return estanciaRepository.save(estancia) != null;
	}

	public List<Estancia> findByPeregrinoId(Long peregrinoId) {
		return estanciaRepository.findByPeregrinoId(peregrinoId);
	}
}
