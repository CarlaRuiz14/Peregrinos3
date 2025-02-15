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

	public Peregrino save(Peregrino entidad) {
		return peregrinoRepository.save(entidad);
	}

	public Peregrino findByIdUsuario(long id) {
		return peregrinoRepository.findByUsuario_id(id);
	}
	
	public Peregrino findByNameUsuario(String nombreUsuario) {
		return peregrinoRepository.findByUsuario_nombreUsuario(nombreUsuario);
	}

	public List<Peregrino> findAll() {
		return peregrinoRepository.findAll();
	}

	@Transactional
	public void registrarUsuarioCarnetYPeregrino(String usuario, String email, String contraseña, Parada paradaInicial,
			String nombrePer, String apellidos, String nacionalidad) {

		Usuario user = new Usuario(usuario, email, contraseña, Perfil.PEREGRINO);
		usuarioService.saveConPassword(user);

		Carnet carnet = new Carnet(paradaInicial);

		Peregrino peregrino = new Peregrino(nombrePer, apellidos, nacionalidad, user, carnet);

		carnet.setPeregrino(peregrino);

		peregrinoRepository.save(peregrino);

	}

	public void actualizarDatosPeregrino(Peregrino peregrinoActivo) {
		
		save(peregrinoActivo);
	}
}
