package com.luisdbb.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;

public interface EstanciaCustomRepository {
	
	List<Estancia> findByIdParadaAndFechaBetween(Long paradaId, LocalDate fechaInicio, LocalDate fechaFin);

}
