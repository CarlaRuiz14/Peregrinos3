package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;

/**
 * Repositorio para la entidad {@link Carnet}. Proporciona operaciones de acceso
 * a datos mediante JPA.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Repository
public interface CarnetRepository extends JpaRepository<Carnet, Long> {

	/**
	 * Busca un carnet por su identificador único.
	 * 
	 * @param id Identificador único del carnet.
	 * @return El carnet encontrado o {@code null} si no existe.
	 */
	Carnet findById(long id);
}
