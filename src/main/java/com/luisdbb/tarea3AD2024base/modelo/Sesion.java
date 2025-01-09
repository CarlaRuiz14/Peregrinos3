package com.luisdbb.tarea3AD2024base.modelo;

import org.springframework.stereotype.Component;

import com.luisdbb.tarea3AD2024base.config.Perfil;

@Component
// por defecto en spring usa singleton al llevar anotación component
public class Sesion {

	// atributos
	private Usuario usuarioActivo = null;
	private Perfil perfilActivo = Perfil.INVITADO;

	// constructores
	public Sesion() {
		super();
	}

	public Sesion(Usuario usuarioActivo, Perfil perfilActivo) {
		super();
		this.usuarioActivo = usuarioActivo;
		this.perfilActivo = perfilActivo;
	}

	// getters y setters
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
