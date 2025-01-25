package com.luisdbb.tarea3AD2024base.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private Alertas alertas;

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

	public boolean validarFechas(LocalDate fechaInicio, LocalDate fechaFin) {
		LocalDate hoy = LocalDate.now();

		if (fechaInicio == null || fechaFin == null) {
			alertas.alertaError("Error", "Debes seleccionar ambas fechas.");
			return false;
		}

		if (fechaInicio.isAfter(hoy)) {
			alertas.alertaError("Error", "La fecha de inicio debe ser anterior al día de hoy.");
			return false;
		}

		if (fechaFin.isAfter(hoy)) {
			alertas.alertaError("Error", "La fecha de fin debe ser anterior al día de hoy.");
			return false;
		}

		if (fechaInicio.isAfter(fechaFin)) {
			alertas.alertaError("Error", "La fecha de inicio debe ser anterior a la fecha de fin.");
			return false;
		}
		return true;
	}

	public boolean validarCredenciales(String usuario, String contraseña) {
		if (usuario == null || usuario.isEmpty()) {
			alertas.alertaError("Faltan datos", "El campo usuario es obligatorio.");
			return false;
		}
		if (contraseña == null || contraseña.isEmpty()) {
			alertas.alertaError("Faltan datos", "El campo contraseña es obligatorio.");
			return false;
		}
		return true;
	}

	public boolean validarContraseñas(String contraseña, String confirmacion) {
		return contraseña.equals(confirmacion);
	}

	public boolean validarRegion(String region) {	   
	    return region.length() == 1 && Character.isLetter(region.charAt(0));
	}

}
