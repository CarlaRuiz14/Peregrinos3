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
 * Representa a un peregrino y sus datos asociados, como nombre, apellidos,
 * nacionalidad, usuario, carnet, estancias y paradas visitadas.
 * 
 * Atributos:
 * <ul>
 * <li><b>id:</b> Identificador único del peregrino.</li>
 * <li><b>nombre:</b> Nombre del peregrino.</li>
 * <li><b>apellidos:</b> Apellidos del peregrino.</li>
 * <li><b>nacionalidad:</b> Nacionalidad del peregrino.</li>
 * <li><b>usuario:</b> Usuario asociado al peregrino.</li>
 * <li><b>carnet:</b> Carnet asociado al peregrino.</li>
 * <li><b>estancias:</b> Lista de estancias realizadas por el peregrino.</li>
 * <li><b>paradasPeregrino:</b> Lista de paradas visitadas por el
 * peregrino.</li>
 * </ul>
 * 
 * Relaciones:
 * <ul>
 * <li><b>OneToOne:</b> Relación bidireccional con Carnet (cascada ALL).</li>
 * <li><b>OneToOne:</b> Relación unidireccional con Usuario (cascada MERGE,
 * REMOVE).</li>
 * <li><b>OneToMany:</b> Relación con Estancias (cascada ALL).</li>
 * <li><b>OneToMany:</b> Relación con ParadasPeregrino (cascada ALL).</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */

@Entity
@Table(name = "peregrinos")
public class Peregrino {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nombre;
	private String apellidos;
	private String nacionalidad;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_carnet", nullable = false, unique = true)
	private Carnet carnet;

	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
	private List<Estancia> estancias;

	// para save de usuario independiente de peregrino y poder encriptar contraseña
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(name = "id_usuario", nullable = false, unique = true)
	private Usuario usuario;

	@OneToMany(mappedBy = "peregrino", cascade = CascadeType.ALL)
	private List<ParadasPeregrino> paradasPeregrino;

	public Peregrino() {
		super();
	}

	public Peregrino(String nombre, String apellidos, String nacionalidad, Usuario usuario, Carnet carnet) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nacionalidad = nacionalidad;
		this.usuario = usuario;
		this.carnet = carnet;
	}

	public Peregrino(long id, String nombre, String apellidos, String nacionalidad, Usuario usuario,
			List<Estancia> estancias, Carnet carnet, List<ParadasPeregrino> paradasPeregrino) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nacionalidad = nacionalidad;
		this.usuario = usuario;
		this.estancias = estancias;
		this.carnet = carnet;
		this.paradasPeregrino = paradasPeregrino;
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

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Estancia> getEstancias() {
		return estancias;
	}

	public void setEstancias(List<Estancia> estancias) {
		this.estancias = estancias;
	}

	public Carnet getCarnet() {
		return carnet;
	}

	public void setCarnet(Carnet carnet) {
		this.carnet = carnet;
	}

	public List<ParadasPeregrino> getParadasPeregrino() {
		return paradasPeregrino;
	}

	public void setParadasPeregrino(List<ParadasPeregrino> paradasPeregrino) {
		this.paradasPeregrino = paradasPeregrino;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apellidos, carnet, estancias, id, nacionalidad, nombre, paradasPeregrino, usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Peregrino other = (Peregrino) obj;
		return Objects.equals(apellidos, other.apellidos) && Objects.equals(carnet, other.carnet)
				&& Objects.equals(estancias, other.estancias) && id == other.id
				&& Objects.equals(nacionalidad, other.nacionalidad) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(paradasPeregrino, other.paradasPeregrino) && Objects.equals(usuario, other.usuario);
	}

	@Override
	public String toString() {
		return "Peregrino [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", nacionalidad="
				+ nacionalidad + ", carnet=" + carnet + ", estancias=" + estancias + ", usuario=" + usuario
				+ ", paradasPeregrino=" + paradasPeregrino + "]";
	}
}
