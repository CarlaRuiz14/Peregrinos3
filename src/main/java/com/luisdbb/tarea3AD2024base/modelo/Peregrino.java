package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;
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
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Entity
@Table(name = "peregrinos")
public class Peregrino {

	// atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable=false)
	private String nombre;	
	private String apellidos;
	@Column(name = "fecha_nacimiento",nullable=false)
	private LocalDate fechaNacimiento;
	private String genero;
	private String nacionalidad;

	// relacion uno a uno con carnet bidireccional
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_carnet",nullable = false, unique = true)
	private Carnet carnet;

	// relacion uno a muchos con estancias
	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL) // Relación uno a muchos
	private List<Estancia> estancias;

	// relacion uno a uno con usuario unidireccional
	// para save de usuario independiente de peregrino y poder encriptar contraseña
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(name = "id_usuario", nullable = false, unique = true) // FK a User,especificar unique y nullable, no
																		// implicito en FK
	private Usuario usuario; // de User se saca la PK para la FK aqui y el nombre de la columna sera este

	// relacion uno a muchos con las paradas de paradasperegrinos
	@OneToMany(mappedBy = "peregrino", cascade = CascadeType.ALL)
	private List<ParadasPeregrino> paradasPeregrino;

	// constructores
	public Peregrino() {
		super();
	}

	// constructor para prueba en sellarControler
	public Peregrino(long id, String nombre, String nacionalidad, Carnet carnet) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.carnet = carnet;
	}

	
	public Peregrino(String nombre, String apellidos, LocalDate fechaNacimiento, String genero,
			String nacionalidad, Usuario usuario, Carnet carnet) {
		super();		
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.genero = genero;
		this.nacionalidad = nacionalidad;
		this.usuario = usuario;		
		this.carnet = carnet;
		
	}
	
	public Peregrino(long id, String nombre, String apellidos, LocalDate fechaNacimiento, String genero,
			String nacionalidad, Usuario usuario, List<Estancia> estancias, Carnet carnet,
			List<ParadasPeregrino> paradasPeregrino) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fechaNacimiento = fechaNacimiento;
		this.genero = genero;
		this.nacionalidad = nacionalidad;
		this.usuario = usuario;
		this.estancias = estancias;
		this.carnet = carnet;
		this.paradasPeregrino = paradasPeregrino;
	}

	// getters y setters
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

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
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

	// métodos
	@Override
	public int hashCode() {
		return Objects.hash(apellidos, carnet, estancias, fechaNacimiento, genero, id, nacionalidad, nombre,
				paradasPeregrino, usuario);
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
				&& Objects.equals(estancias, other.estancias) && Objects.equals(fechaNacimiento, other.fechaNacimiento)
				&& Objects.equals(genero, other.genero) && id == other.id
				&& Objects.equals(nacionalidad, other.nacionalidad) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(paradasPeregrino, other.paradasPeregrino) && Objects.equals(usuario, other.usuario);
	}

	@Override
	public String toString() {
		return "Peregrino [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", fechaNacimiento="
				+ fechaNacimiento + ", genero=" + genero + ", nacionalidad=" + nacionalidad + ", carnet=" + carnet
				+ ", estancias=" + estancias + ", usuario=" + usuario + ", paradasPeregrino=" + paradasPeregrino + "]";
	}

}
