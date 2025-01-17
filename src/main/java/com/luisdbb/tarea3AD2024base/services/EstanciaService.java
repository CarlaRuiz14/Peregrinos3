package com.luisdbb.tarea3AD2024base.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.repositorios.EstanciaRepository;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class EstanciaService {

	@Autowired
	private EstanciaRepository estanciaRepository;

	public List<Estancia> findByIdParadaAndFechaBetween(Long id, LocalDate fechaI, LocalDate fechaF) {
		return estanciaRepository.findByIdParadaAndFechaBetween(id, fechaI, fechaF);
	}

}
