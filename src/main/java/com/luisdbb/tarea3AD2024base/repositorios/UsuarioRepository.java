package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Usuario;

/**
 * Repositorio para la gestión de la entidad {@link Usuario} mediante JPA.
 * Proporciona operaciones para recuperar y verificar usuarios en la base de
 * datos.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	/**
	 * Busca un usuario a partir de su nombre de usuario.
	 * 
	 * @param nombreUsuario Nombre del usuario.
	 * @return Usuario asociado al nombre dado.
	 */
	Usuario findByNombreUsuario(String nombreUsuario);

	/**
	 * Verifica si existe un usuario con el nombre dado.
	 * 
	 * @param nombre Nombre del usuario a verificar.
	 * @return {@code true} si el usuario existe, {@code false} en caso contrario.
	 */
	boolean existsByNombreUsuario(String nombre);
}
