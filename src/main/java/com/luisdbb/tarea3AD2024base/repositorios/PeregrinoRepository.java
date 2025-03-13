package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;

/**
 * Repositorio para la gestión de la entidad {@link Peregrino} mediante JPA.
 * Proporciona operaciones para recuperar peregrinos a partir de su usuario
 * asociado.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Repository
public interface PeregrinoRepository extends JpaRepository<Peregrino, Long> {

	/**
	 * Busca un peregrino a partir del identificador del usuario asociado.
	 * 
	 * @param id Identificador del usuario.
	 * @return Peregrino asociado al usuario.
	 */
	Peregrino findByUsuario_id(long id);

	/**
	 * Busca un peregrino a partir del nombre de usuario asociado.
	 * 
	 * @param nombreUsuario Nombre del usuario.
	 * @return Peregrino asociado al usuario con el nombre dado.
	 */
	Peregrino findByUsuario_nombreUsuario(String nombreUsuario);
	
}
