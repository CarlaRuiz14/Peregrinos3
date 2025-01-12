package com.luisdbb.tarea3AD2024base.config;

public class Validaciones {

	/**
	 * Método que valida si un String contiene espacios en blanco.
	 * 
	 * @param usuario
	 * @return true si no contiene espacios en blanco.
	 */
	public static boolean validarEspacios(String usuario) {
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
	public static boolean validarContraseña(String contraseña) {
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
	public static boolean validarEmail(String email) {
	    String formato = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	    return email.matches(formato);
	}


}
