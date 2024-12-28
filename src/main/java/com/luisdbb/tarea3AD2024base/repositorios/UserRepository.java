package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.User;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
