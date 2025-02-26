package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrino;
import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrinoId;

/**
 * Repositorio para la gestión de la relación entre peregrinos y paradas
 * mediante JPA. Proporciona operaciones para recuperar los registros en los que
 * un peregrino ha realizado paradas.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Repository
public interface ParadasPeregrinoRepository extends JpaRepository<ParadasPeregrino, ParadasPeregrinoId> {

	/**
	 * Busca todas las paradas registradas en las que un peregrino ha estado.
	 * 
	 * @param idPeregrino Identificador del peregrino.
	 * @return Lista de registros de paradas asociadas al peregrino.
	 */
	List<ParadasPeregrino> findByIdIdPeregrino(Long idPeregrino);
}
