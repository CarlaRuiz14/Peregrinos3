package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
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

	// cascade = CascadeType.ALL

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nombre;
	private String apellidos;
	private LocalDate fechaNacimiento;
	private String genero;
	private String nacionalidad;
	private String email;
	
	// relacion uno a uno con user
	@OneToOne (cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, unique = true) // Clave foránea a User, especificar unique y nullable, no implicito en FK
	private Usuario usuario; // de User se saca la PK para la FK aqui y el nombre de la columna sera este
	// relacion uno a muchos con estancias
	@OneToMany(mappedBy = "peregrino", cascade = CascadeType.ALL) // Relación uno a muchos
	private List<Estancia> estancias;
	// relacion uno a uno con carnet
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, unique = true)
	private Carnet carnet;
	// relacion uno a muchos con las paradas de paradasperegrinos
//	@OneToMany(mappedBy = "idPeregrino") 
//	private List<ParadasPeregrinos> paradas;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Peregrino() {
		super();
	}
	public Peregrino(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	
	
	
	public Peregrino(long id, String nombre, String nacionalidad, Carnet carnet) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.carnet = carnet;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
	
	
	

}
