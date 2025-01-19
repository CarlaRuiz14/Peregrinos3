package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

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

	public boolean existeParada(String nombre, char region) {

		Parada parada = findByNombre(nombre);
		if (parada == null) {
			return false;
		}

		return parada.getRegion() == region;
	}

	public Parada findByNombre(String parada) {
		return paradaRepository.findByNombre(parada);
	}

	public Parada findById(Long id) {
		return paradaRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No se encontró la parada con ID: " + id));

	}
	
	public Parada findByUsuario(Long id) {
		return paradaRepository.findByIdUsuario(id);
				
	}
	

	public List<Parada> findAll() {
		return paradaRepository.findAll();
	}
}
