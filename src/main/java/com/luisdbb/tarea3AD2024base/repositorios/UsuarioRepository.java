package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Usuario;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByNombreUsuario(String nombreUsuario);
	
	boolean existsByNombreUsuario(String nombre);	
}
