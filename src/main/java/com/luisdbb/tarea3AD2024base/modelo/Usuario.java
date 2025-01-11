package com.luisdbb.tarea3AD2024base.modelo;

import java.util.Objects;

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
	@Column(name = "id")
	private long id;

	@Column(name = "nombre_usuario", nullable = false,unique = true)
	private String nombreUsuario;

	private String email;

	@Column(nullable = false)
	private String contraseña;

	@Column(nullable = false)
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

	// getters y setters
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

	// métodos
	@Override
	public int hashCode() {
		return Objects.hash(contraseña, email, id, nombreUsuario, perfil);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(contraseña, other.contraseña) && Objects.equals(email, other.email) && id == other.id
				&& Objects.equals(nombreUsuario, other.nombreUsuario) && perfil == other.perfil;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombreUsuario=" + nombreUsuario + ", email=" + email + ", contraseña="
				+ contraseña + ", perfil=" + perfil + "]";
	}

}
