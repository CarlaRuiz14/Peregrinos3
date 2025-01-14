package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoRepository;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class PeregrinoService {

	@Autowired
	private PeregrinoRepository peregrinoRepository;
	
	public Peregrino save(Peregrino entidad) {
		return peregrinoRepository.save(entidad);
	}
	
	public List<Peregrino> findAll() {
		return peregrinoRepository.findAll();
	}
	
	
}
