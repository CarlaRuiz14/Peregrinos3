package com.luisdbb.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;

@Repository
public interface EstanciaRepository
		extends JpaRepository<Estancia, Long> {

	List<Estancia> findByPeregrinoId(Long peregrinoId);
	
	 List<Estancia> findByParadaIdAndFechaBetween(Long paradaId, LocalDate fechaInicio, LocalDate fechaFin);
	

}
