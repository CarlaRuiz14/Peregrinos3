package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrino;
import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrinoId;

@Repository
public interface ParadasPeregrinoRepository extends JpaRepository<ParadasPeregrino, ParadasPeregrinoId>{

    List<ParadasPeregrino> findByIdIdPeregrino(Long idPeregrino);
}
