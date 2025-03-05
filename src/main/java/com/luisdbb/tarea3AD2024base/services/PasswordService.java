package com.luisdbb.tarea3AD2024base.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio para gestionar la encriptación y verificación de contraseñas
 * utilizando el algoritmo BCrypt.
 * 
 * @author Carla Ruiz
 * @since 14/01/2025
 */
@Service
public class PasswordService {

	private final BCryptPasswordEncoder passwordEncoder;

	/**
	 * Constructor de la clase PasswordService. Inicializa el encoder de contraseñas
	 * utilizando BCrypt.
	 */
	public PasswordService() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	/**
	 * Encripta una contraseña en texto plano utilizando el algoritmo BCrypt.
	 *
	 * @param password La contraseña en texto plano que se desea encriptar.
	 * @return Un hash encriptado de la contraseña.
	 */
	public String encriptar(String password) {
		return passwordEncoder.encode(password);
	}

	/**
	 * Verifica si una contraseña en texto plano coincide con un hash encriptado.
	 *
	 * @param userPassword La contraseña en texto plano proporcionada por el
	 *                     usuario.
	 * @param bdPassword   El hash encriptado almacenado en la base de datos.
	 * @return true - si la contraseña coincide con el hash.
	 */
	public boolean verificar(String userPassword, String bdPassword) {
	    return passwordEncoder.matches(userPassword, bdPassword);	   
	}
}
