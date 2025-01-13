package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.repositorios.CarnetRepository;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class CarnetService {
	
	@Autowired
	private CarnetRepository carnetRepository;
	
	public Carnet save(Carnet entidad) {
		return carnetRepository.save(entidad);
	}

}
