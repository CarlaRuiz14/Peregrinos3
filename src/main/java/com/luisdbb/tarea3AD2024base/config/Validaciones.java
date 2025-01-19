package com.luisdbb.tarea3AD2024base.config;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validaciones {
	
	@Autowired
	private Alertas alertas;

	/**
	 * Método que valida si un String contiene espacios en blanco.
	 * 
	 * @param usuario
	 * @return true si no contiene espacios en blanco.
	 */
	public boolean validarEspacios(String usuario) {
		return !usuario.contains(" ");
	}

	/**
	 * Valida el formato de una contraseña.
	 * 
	 * La contraseña debe cumplir con los siguientes requisitos:
	 * - Contener al menos una letra minúscula.
	 * - Contener al menos una letra mayúscula.
	 * - Contener al menos un número.
	 * - Contener al menos un carácter especial (@, $, !, %, *, ?, &, -, _).
	 * - Tener una longitud mínima de 6 caracteres.
	 * 
	 * @param contraseña
	 * @return true si la contraseña cumple con los requisitos.
	 */
	public boolean validarContraseña(String contraseña) {
	    String formato = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&-_])[A-Za-z\\d@$!%*?&-_]{6,}$";
	    return contraseña.matches(formato);
	}


	/**
	 * Valida el formato de una dirección de correo electrónico.
	 * 
	 * La dirección de correo debe cumplir con los siguientes requisitos:
	 * - Iniciar con caracteres alfanuméricos, que pueden incluir puntos (.), guiones bajos (_), 
	 *   porcentajes (%), signos más (+) y guiones (-).
	 * - Contener el símbolo '@' seguido de un dominio válido.
	 * - El dominio debe incluir al menos un punto '.' separando el nombre del dominio y la extensión.
	 * - La extensión del dominio debe tener al menos 2 caracteres alfabéticos (por ejemplo, .com, .es, .org).
	 * 
	 * @param email
	 * @return true si el correo electrónico cumple los requisitos.
	 */
	public boolean validarEmail(String email) {
	    String formato = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	    return email.matches(formato);
	}
	
	public boolean validarFechas(LocalDate fechaInicio,LocalDate fechaFin) {
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
	
	  /**
     * Valida si los campos de usuario y contraseña están rellenados.
     * 
     * @param usuario Nombre de usuario.
     * @param contraseña Contraseña del usuario.
     * @return `true` si ambos campos están rellenados, `false` de lo contrario.
     */
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
	    return region.length()==1;
	}

}
