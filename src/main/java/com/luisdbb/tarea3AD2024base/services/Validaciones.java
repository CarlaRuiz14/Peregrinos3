package com.luisdbb.tarea3AD2024base.services;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

/**
 * Clase para gestionar las validaciones comunes en la aplicación.
 * 
 * <b>Responsabilidades principales:</b>
 * <ul>
 * <li>Validar formatos de entrada como nombres, apellidos, contraseñas, correos electrónicos y regiones.</li>
 * <li>Validar la consistencia de fechas.</li>
 * <li>Gestionar la validación de credenciales y coincidencia de contraseñas.</li>
 * </ul>
 * 
 * <b>Métodos destacados:</b>
 * <ul>
 * <li><b>validarEspacios:</b> Comprueba si un texto no contiene espacios en blanco.</li>
 * <li><b>validarNombreYApellidos:</b> Verifica que el nombre y apellidos solo contengan caracteres válidos.</li>
 * <li><b>validarContraseña:</b> Valida el formato de una contraseña según requisitos específicos (mayúscula, minúscula, número y carácter especial).</li>
 * <li><b>validarEmail:</b> Comprueba si un correo electrónico cumple con un formato válido.</li>
 * <li><b>validarFechas:</b> Verifica que un rango de fechas sea válido y lógico, asegurando que la fecha de inicio no sea posterior a la fecha de fin ni mayor a la fecha actual.</li>
 * <li><b>validarCredenciales:</b> Asegura que el usuario y la contraseña no estén vacíos.</li>
 * <li><b>validarContraseñas:</b> Compara si dos contraseñas coinciden.</li>
 * <li><b>validarRegion:</b> Valida que una región tenga exactamente un carácter y sea una letra.</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Component
public class Validaciones {

	public boolean validarEspacios(String usuario) {
		return !usuario.contains(" ");
	}
	
	public boolean validarNombreYApellidos(String nombre) {
		String formato="^[A-Za-zÁÉÍÓÚáéíóúÑñüÜ' -]+$";
		return nombre.matches(formato);
	}

	public boolean validarContraseña(String contraseña) {
		String formato = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&-_])[A-Za-z\\d@$!%*?&-_]{6,}$";
		return contraseña.matches(formato);
	}

	public boolean validarEmail(String email) {
		String formato = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		return email.matches(formato);
	}

	public int validarFechas(LocalDate fechaInicio, LocalDate fechaFin) {
		LocalDate hoy = LocalDate.now();

		if (fechaInicio == null || fechaFin == null) {
			return 1;
		}

		if (fechaInicio.isAfter(hoy)) {
			return 2;
		}

		if (fechaFin.isAfter(hoy)) {
			return 3;
		}

		if (fechaInicio.isAfter(fechaFin)) {
			return 4;
		}
		return 0;
	}

	public int validarCredenciales(String usuario, String contraseña) {
		if (usuario == null || usuario.isEmpty()) {
			return 1;
		}
		if (contraseña == null || contraseña.isEmpty()) {
			return 2;
		}
		return 0;
	}

	public boolean validarContraseñas(String contraseña, String confirmacion) {
		return contraseña.equals(confirmacion);
	}

	public boolean validarRegion(String region) {	   
	    return region.length() == 1 && Character.isLetter(region.charAt(0));
	}
	
	
}
