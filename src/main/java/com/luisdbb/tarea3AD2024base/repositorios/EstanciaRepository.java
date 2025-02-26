package com.luisdbb.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;

/**
 * Repositorio para la gestión de estancias en paradas mediante JPA. Proporciona
 * operaciones para buscar estancias de peregrinos y filtrar por fecha y parada.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Repository
public interface EstanciaRepository extends JpaRepository<Estancia, Long> {

	/**
	 * Obtiene todas las estancias registradas de un peregrino.
	 * 
	 * @param peregrinoId Identificador del peregrino.
	 * @return Lista de estancias asociadas al peregrino.
	 */
	List<Estancia> findByPeregrinoId(Long peregrinoId);

	/**
	 * Obtiene todas las estancias registradas en una parada dentro de un rango de
	 * fechas.
	 * 
	 * @param paradaId    Identificador de la parada.
	 * @param fechaInicio Fecha de inicio del rango.
	 * @param fechaFin    Fecha de fin del rango.
	 * @return Lista de estancias que cumplen con los criterios de búsqueda.
	 */
	List<Estancia> findByParadaIdAndFechaBetween(Long paradaId, LocalDate fechaInicio, LocalDate fechaFin);

}
