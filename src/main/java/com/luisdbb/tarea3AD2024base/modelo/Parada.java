package com.luisdbb.tarea3AD2024base.modelo;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Representa una parada, asociada a un usuario y a peregrinos que la visitan.
 * 
 * Atributos:
 * <ul>
 * <li><b>id:</b> Identificador único de la parada.</li>
 * <li><b>nombre:</b> Nombre de la parada.</li>
 * <li><b>region:</b> Región a la que pertenece la parada.</li>
 * <li><b>paradasPeregrino:</b> Relación con los peregrinos que han pasado por
 * esta parada.</li>
 * <li><b>usuario:</b> Usuario responsable de la parada.</li>
 * </ul>
 * 
 * Relaciones:
 * <ul>
 * <li><b>OneToMany:</b> ParadasPeregrino (relación con registros de peregrinos
 * que visitan la parada).</li>
 * <li><b>OneToOne:</b> Usuario (relación obligatoria y única con el responsable
 * de la parada).</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */

@Entity
@Table(name = "paradas")
public class Parada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private char region;

	@OneToMany(mappedBy = "parada", cascade = CascadeType.ALL)
	private List<ParadasPeregrino> paradasPeregrino;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario", nullable = false, unique = true)
	private Usuario usuario;

	public Parada() {
		super();
	}

	public Parada(long id, String nombre, char region) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.region = region;
	}

	public Parada(String nombre, char region, Usuario usuario) {
		super();
		this.nombre = nombre;
		this.region = region;
		this.usuario = usuario;
	}

	public Parada(long id, String nombre, char region, List<ParadasPeregrino> paradasPeregrino, Usuario usuario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.region = region;
		this.paradasPeregrino = paradasPeregrino;
		this.usuario = usuario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public char getRegion() {
		return region;
	}

	public void setRegion(char region) {
		this.region = region;
	}

	public List<ParadasPeregrino> getParadasPeregrino() {
		return paradasPeregrino;
	}

	public void setParadasPeregrino(List<ParadasPeregrino> paradasPeregrino) {
		this.paradasPeregrino = paradasPeregrino;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, paradasPeregrino, region, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parada other = (Parada) obj;
		return id == other.id && Objects.equals(nombre, other.nombre)
				&& Objects.equals(paradasPeregrino, other.paradasPeregrino) && Objects.equals(region, other.region)
				&& Objects.equals(usuario, other.usuario);
	}

	@Override
	public String toString() {
		return nombre;
	}
}
