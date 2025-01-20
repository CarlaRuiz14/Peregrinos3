package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import com.luisdbb.tarea3AD2024base.modelo.Parada;

public interface ParadaCustomRepository {
	
	public Parada findByIdUsuario(Long id);

	public List<Parada> findParadasByPeregrinoId(Long peregrinoId);

}
