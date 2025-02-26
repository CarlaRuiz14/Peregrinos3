package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Parada;

/**
 * Repositorio para la gestión de paradas mediante JPA. Proporciona operaciones
 * para buscar paradas por nombre, identificador y usuario asociado.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Repository
public interface ParadaRepository extends JpaRepository<Parada, Long> {

	/**
	 * Busca una parada por su nombre.
	 * 
	 * @param nombre Nombre de la parada.
	 * @return Parada encontrada o null si no existe.
	 */
	Parada findByNombre(String nombre);

	/**
	 * Busca una parada por su identificador único.
	 * 
	 * @param id Identificador de la parada.
	 * @return Parada correspondiente al identificador o null si no existe.
	 */
	Parada findParadaById(Long id);

	/**
	 * Busca una parada asociada a un usuario en función de su ID.
	 * 
	 * @param id Identificador del usuario.
	 * @return Parada gestionada por el usuario o null si no tiene asignada ninguna.
	 */
	Parada findByUsuarioId(Long id);

}
