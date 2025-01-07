package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
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
@Table(name = "paradas")
public class Parada {

	// atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nombre;

	private Character region;

	@OneToMany(mappedBy = "parada")
	private List<ParadasPeregrino> paradasPeregrino = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, unique = true)
	private Usuario usuario;

	@OneToMany(mappedBy = "parada")
	private List<Estancia> estancias = new ArrayList<>();

	// constructores
	public Parada() {
		super();
	}

	public Parada(long id, String nombre, Character region) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.region = region;
	}

	public Parada(long id, String nombre, Character region, List<ParadasPeregrino> paradasPeregrino, Usuario usuario,
			List<Estancia> estancias) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.region = region;
		this.paradasPeregrino = paradasPeregrino;
		this.usuario = usuario;
		this.estancias = estancias;
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

	public Character getRegion() {
		return region;
	}

	public void setRegion(Character region) {
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

	public List<Estancia> getEstancias() {
		return estancias;
	}

	public void setEstancias(List<Estancia> estancias) {
		this.estancias = estancias;
	}

	

}
