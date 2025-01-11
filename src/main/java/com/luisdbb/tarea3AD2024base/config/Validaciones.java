package com.luisdbb.tarea3AD2024base.config;

public class Validaciones {

	public static boolean validarEspacios(String usuario) {
		return !usuario.contains(" ");
	}

	public static boolean validarContraseña(String contraseña) {
		String formato = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&-_])[A-Za-z\\d@$!%*?&-_]{6,}$";
		return contraseña.matches(formato);

	}

	public static boolean validarEmail(String email) {
		String formato = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		return email.matches(formato);
	}

}
