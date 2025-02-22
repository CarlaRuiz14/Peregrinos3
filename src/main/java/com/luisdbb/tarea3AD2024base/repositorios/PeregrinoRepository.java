package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Peregrino;

@Repository
public interface PeregrinoRepository extends JpaRepository<Peregrino, Long>{

	Peregrino findByUsuario_id(long id);
	
	Peregrino findByUsuario_nombreUsuario(String nombreUsuario);
}
