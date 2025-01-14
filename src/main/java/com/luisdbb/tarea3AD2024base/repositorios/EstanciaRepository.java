package com.luisdbb.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;

public interface EstanciaRepository extends JpaRepository<Estancia, Long>{
	
	@Query("SELECT e FROM Estancia e WHERE e.parada.id = :paradaId AND e.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Estancia> findByIdParadaAndFechaBetween(
            @Param("paradaId") Long paradaId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );

}
