package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luisdbb.tarea3AD2024base.modelo.Parada;

public interface ParadaRepository extends JpaRepository<Parada, Long>{
	Parada findByNombre(String nombre);
	
	@Query("SELECT p FROM Parada p WHERE p.usuario.id = :idUsuario")
	Parada buscarPorIdUsuario(@Param("idUsuario") Long idUsuario);

	
}
