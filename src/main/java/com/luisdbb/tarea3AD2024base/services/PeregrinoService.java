package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoRepository;

import jakarta.transaction.Transactional;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class PeregrinoService {

	@Autowired
	private PeregrinoRepository peregrinoRepository;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PeregrinoService peregrinoService;

	public Peregrino save(Peregrino entidad) {
		return peregrinoRepository.save(entidad);
	}

	public List<Peregrino> findAll() {
		return peregrinoRepository.findAll();
	}

	@Transactional
	public void registrarUsuarioCarnetYPeregrino(String usuario, String email, String contraseña, Parada paradaInicial,
			String nombrePer, String apellidos, String nacionalidad) {

		Usuario user = new Usuario(usuario, email, contraseña, Perfil.PEREGRINO);
		usuarioService.save(user);

		Carnet carnet = new Carnet(paradaInicial);

		Peregrino peregrino = new Peregrino(nombrePer, apellidos, nacionalidad, user, carnet);

		carnet.setPeregrino(peregrino);

		peregrinoService.save(peregrino);

	}

}
