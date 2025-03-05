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
 * Servicio para la gestión de peregrinos en la aplicación.
 * 
 * <ul>
 * <li>Guarda y actualiza peregrinos en la base de datos.</li>
 * <li>Busca peregrinos por su ID de usuario o nombre de usuario.</li>
 * <li>Obtiene la lista de todos los peregrinos registrados.</li>
 * <li>Registra un nuevo peregrino junto con su usuario y carnet.</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class PeregrinoService {

	@Autowired
	private PeregrinoRepository peregrinoRepository;

	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Guarda un peregrino en la base de datos.
	 * 
	 * @param entidad Peregrino a guardar.
	 * @return Peregrino guardado o {@code null} si no se guarda correctamente.
	 */
	public Peregrino save(Peregrino entidad) {
		return peregrinoRepository.save(entidad);
	}

	/**
	 * Busca un peregrino por el ID de su usuario.
	 * 
	 * @param id ID del usuario asociado al peregrino.
	 * @return Peregrino encontrado o {@code null} si no existe.
	 */
	public Peregrino findByIdUsuario(long id) {
		return peregrinoRepository.findByUsuario_id(id);
	}

	/**
	 * Busca un peregrino por el nombre de usuario de su cuenta.
	 * 
	 * @param nombreUsuario Nombre de usuario asociado al peregrino.
	 * @return Peregrino encontrado o {@code null} si no existe.
	 */
	public Peregrino findByNameUsuario(String nombreUsuario) {
		return peregrinoRepository.findByUsuario_nombreUsuario(nombreUsuario);
	}

	/**
	 * Obtiene la lista de todos los peregrinos registrados en la base de datos.
	 * 
	 * @return Lista de peregrinos o una lista vacía si no hay registros.
	 */
	public List<Peregrino> findAll() {
		return peregrinoRepository.findAll();
	}

	/**
	 * Registra un nuevo peregrino junto con su usuario y carnet.
	 * 
	 * @param usuario       Nombre de usuario.
	 * @param email         Dirección de correo electrónico.
	 * @param contraseña    Contraseña del usuario.
	 * @param paradaInicial Parada inicial del peregrino.
	 * @param nombrePer     Nombre del peregrino.
	 * @param apellidos     Apellidos del peregrino.
	 * @param nacionalidad  Nacionalidad del peregrino.
	 */
	@Transactional
	public void registrarUsuarioCarnetYPeregrino(String usuario, String email, String contraseña, Parada paradaInicial,
			String nombrePer, String apellidos, String nacionalidad) {

		if (usuarioService.existsByNombreUsuario(usuario)) {
			throw new RuntimeException("El usuario ya existe");
		}

		Usuario user = new Usuario(usuario, email, contraseña, Perfil.PEREGRINO);
		usuarioService.saveConPassword(user);

		Carnet carnet = new Carnet(paradaInicial);

		Peregrino peregrino = new Peregrino(nombrePer, apellidos, nacionalidad, user, carnet);

		carnet.setPeregrino(peregrino);

		peregrinoRepository.save(peregrino);

	}

	/**
	 * Actualiza los datos de un peregrino en la base de datos.
	 *
	 * @param peregrinoActivo Peregrino con los datos actualizados.
	 * @throws RuntimeException Si el peregrino es nulo o sus datos son inválidos.
	 */
	public void actualizarDatosPeregrino(Peregrino peregrinoActivo) {
	    if (peregrinoActivo == null) {
	        throw new RuntimeException("El peregrino no puede ser nulo.");
	    }

	    save(peregrinoActivo);
	}
	
	

}
