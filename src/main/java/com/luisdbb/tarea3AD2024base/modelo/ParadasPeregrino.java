package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Entity
@Table(name = "paradasPeregrinos")
public class ParadasPeregrino {

	// atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "peregrino_id", nullable = false)
	private Peregrino peregrino;

	@ManyToOne
	@JoinColumn(name = "parada_id", nullable = false)
	private Parada parada;

	private LocalDate fecha;

	// Constructor por defecto
	public ParadasPeregrino() {
	}

	// Constructor con parámetros
	public ParadasPeregrino(Peregrino peregrino, Parada parada, LocalDate fecha) {
		this.peregrino = peregrino;
		this.parada = parada;
		this.fecha = fecha;
	}

	// getters y setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Peregrino getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	public Parada getParada() {
		return parada;
	}

	public void setParada(Parada parada) {
		this.parada = parada;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

}
