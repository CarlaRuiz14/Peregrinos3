package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.ListQuerydslPredicateExecutor;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;

public interface EstanciaRepository
		extends JpaRepository<Estancia, Long>, EstanciaCustomRepository, ListQuerydslPredicateExecutor<Estancia> {

	List<Estancia> findByPeregrinoId(Long peregrinoId);

}
