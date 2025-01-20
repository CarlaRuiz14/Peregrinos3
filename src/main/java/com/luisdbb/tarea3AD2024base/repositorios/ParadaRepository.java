package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.ListQuerydslPredicateExecutor;

import com.luisdbb.tarea3AD2024base.modelo.Parada;

public interface ParadaRepository
		extends JpaRepository<Parada, Long>, ParadaCustomRepository, ListQuerydslPredicateExecutor<Parada> {

	Parada findByNombre(String nombre);
	



}
