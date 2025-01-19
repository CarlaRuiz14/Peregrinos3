package com.luisdbb.tarea3AD2024base.services;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.config.Validaciones;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.repositorios.EstanciaRepository;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class EstanciaService {

	@Autowired
	private EstanciaRepository estanciaRepository;
	@Autowired
	private Validaciones validaciones;

	public List<Estancia> getEstanciasForParada(Long idParada, LocalDate fechaInicio, LocalDate fechaFin) {

		if (!validaciones.validarFechas(fechaInicio, fechaFin)) {
			return Collections.emptyList();
		}

		return estanciaRepository.findByIdParadaAndFechaBetween(idParada, fechaInicio, fechaFin);

	}

	public boolean registrarEstancia(boolean vip, Peregrino peregrino, Parada parada) {

		LocalDate hoy = LocalDate.now();

		Estancia estancia = new Estancia(hoy, vip, peregrino, parada);

		return estanciaRepository.save(estancia) != null;

	}

}
