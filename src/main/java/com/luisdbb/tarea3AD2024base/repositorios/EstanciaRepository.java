package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.ListQuerydslPredicateExecutor;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;

public interface EstanciaRepository
		extends JpaRepository<Estancia, Long>, EstanciaCustomRepository, ListQuerydslPredicateExecutor<Estancia> {

	// no hace falta la firma poque ya esta extendiendo de la custom
	// public List<Estancia> findByIdParadaAndFechaBetween(Long paradaId, LocalDate
	// fechaInicio, LocalDate fechaFin) ;

}
