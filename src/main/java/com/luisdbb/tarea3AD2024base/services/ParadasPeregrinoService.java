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

	@Transactional
	public void registrarParadaInicial(String nombreUsuario, Parada parada) {
		Peregrino peregrino = peregrinoService.findByNameUsuario(nombreUsuario);
		ParadasPeregrino paradaPeregrino = new ParadasPeregrino(peregrino, parada, hoy);
		paradasPeregrinoRepository.save(paradaPeregrino);
	}

	public boolean existeParada(Parada parada, Peregrino peregrino) {
		ParadasPeregrinoId id = new ParadasPeregrinoId(peregrino.getId(), parada.getId(), LocalDate.now());
		return paradasPeregrinoRepository.existsById(id);
	}

	@Transactional
	public void registrarParadaYSellarCarnet(Carnet carnet, Parada parada, Peregrino peregrino) {

		carnet.setDistancia(carnet.getDistancia() + distanciaParadas);
		carnetService.save(carnet);

		ParadasPeregrino paradaPeregrino = new ParadasPeregrino(peregrino, parada, hoy);

		paradasPeregrinoRepository.save(paradaPeregrino);
	}	
	
	
	public List<ParadasPeregrino> listarParadasPeregrino(long idPeregrino){
		return paradasPeregrinoRepository.findByIdIdPeregrino(idPeregrino);
	}
	
	
}
