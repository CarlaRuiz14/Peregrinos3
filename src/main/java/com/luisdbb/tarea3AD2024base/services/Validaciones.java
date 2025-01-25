package com.luisdbb.tarea3AD2024base.services;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

/**
 * Clase para gestionar las validaciones comunes en la aplicación.
 * 
 * <b>Responsabilidades principales:</b>
 * <ul>
 * <li>Validar formatos de entrada como contraseñas, correos electrónicos y
 * regiones.</li>
 * <li>Validar la consistencia de fechas.</li>
 * <li>Gestionar mensajes de alerta en caso de errores.</li>
 * </ul>
 * 
 * <b>Métodos destacados:</b>
 * <ul>
 * <li><b>validarEspacios:</b> Comprueba si un texto no contiene espacios en
 * blanco.</li>
 * <li><b>validarContraseña:</b> Valida el formato de una contraseña según
 * requisitos específicos.</li>
 * <li><b>validarEmail:</b> Comprueba si un correo electrónico cumple con un
 * formato válido.</li>
 * <li><b>validarFechas:</b> Verifica que un rango de fechas sea válido y
 * lógico.</li>
 * <li><b>validarCredenciales:</b> Asegura que usuario y contraseña no estén
 * vacíos.</li>
 * <li><b>validarContraseñas:</b> Compara si dos contraseñas coinciden.</li>
 * <li><b>validarRegion:</b> Valida que una región tenga exactamente un
 * carácter.</li>
 * </ul>
 */

@Component
public class Validaciones {

	public boolean validarEspacios(String usuario) {
		return !usuario.contains(" ");
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
