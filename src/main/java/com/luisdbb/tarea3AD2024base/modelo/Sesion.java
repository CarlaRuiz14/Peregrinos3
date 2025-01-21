package com.luisdbb.tarea3AD2024base.modelo;

import org.springframework.stereotype.Component;

/**
 * Clase para gestionar la sesión activa de un usuario en la aplicación. Utiliza
 * el patrón Singleton por defecto gracias a la anotación {@code @Component} de
 * Spring.
 * 
 * Atributos:
 * <ul>
 * <li><b>usuarioActivo:</b> Usuario que ha iniciado sesión (por defecto,
 * null).</li>
 * <li><b>perfilActivo:</b> Perfil del usuario activo (por defecto,
 * INVITADO).</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */

@Component
public class Sesion {

	private Usuario usuarioActivo = null;
	private Perfil perfilActivo = Perfil.INVITADO;

	public Sesion() {
		super();
	}

	public Sesion(Usuario usuarioActivo, Perfil perfilActivo) {
		super();
		this.usuarioActivo = usuarioActivo;
		this.perfilActivo = perfilActivo;
	}

	public Usuario getUsuarioActivo() {
		return usuarioActivo;
	}

	public void setUsuarioActivo(Usuario usuarioActivo) {
		this.usuarioActivo = usuarioActivo;
	}

	public Perfil getPerfilActivo() {
		return perfilActivo;
	}

	public void setPerfilActivo(Perfil perfilActivo) {
		this.perfilActivo = perfilActivo;
	}
}
