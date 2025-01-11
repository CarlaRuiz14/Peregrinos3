package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.repositorios.ParadaRepository;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class ParadaService {
	
	@Autowired
	private ParadaRepository paradaRepository;

	public Parada save(Parada entidad) {
		return paradaRepository.save(entidad);
	}

	public boolean existeParada(String nombre,char region) {
		Parada parada=paradaRepository.findByNombre(nombre);		
		return parada.getRegion()==region;
	}

}
