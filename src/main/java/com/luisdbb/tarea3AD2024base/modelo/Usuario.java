package com.luisdbb.tarea3AD2024base.modelo;

import com.luisdbb.tarea3AD2024base.config.Perfil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */

@Entity
@Table(name = "Usuarios")
public class Usuario {

	// atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private long id;

	@Column(unique = true)
	private String nombreUsuario;
	
	@Column(unique = true)
	private String email;
	
	private String contraseña;

	@Enumerated(EnumType.STRING)
	private Perfil perfil;

	// constructores
	public Usuario() {
		super();
	}

	public Usuario(long id, String nombreUsuario, String email, String contraseña, Perfil perfil) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.contraseña = contraseña;
		this.perfil = perfil;
	}

	//getters y setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
}
