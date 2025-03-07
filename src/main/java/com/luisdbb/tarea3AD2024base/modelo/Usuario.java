package com.luisdbb.tarea3AD2024base.modelo;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Representa un usuario del sistema con sus datos de acceso y perfil asociado.
 * 
 * Atributos:
 * <ul>
 * <li><b>id:</b> Identificador único del usuario (generado
 * automáticamente).</li>
 * <li><b>nombreUsuario:</b> Nombre de usuario único y obligatorio.</li>
 * <li><b>email:</b> Dirección de correo electrónico obligatoria.</li>
 * <li><b>contraseña:</b> Contraseña del usuario.</li>
 * <li><b>perfil:</b> Perfil del usuario definido en el enum Perfil
 * (obligatorio).</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */

@Entity
@Table(name = "Usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "nombre_usuario", nullable = false, unique = true)
	private String nombreUsuario;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String contraseña;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Perfil perfil;

	public Usuario() {
		super();
	}

	public Usuario(String nombreUsuario, String email, String contraseña, Perfil perfil) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.contraseña = contraseña;
		this.perfil = perfil;
	}

	public Usuario(long id, String nombreUsuario, String email, String contraseña, Perfil perfil) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.email = email;
		this.contraseña = contraseña;
		this.perfil = perfil;
	}

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
