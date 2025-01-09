package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	// clave primaria definida en clase ParadasPeregrinosId
	@Id
	@ManyToOne
	@JoinColumn(name = "id_peregrino", nullable = false)
	private Peregrino peregrino;

	@Id
	@ManyToOne
	@JoinColumn(name = "id_parada", nullable = false)
	private Parada parada;

	@Column(nullable = false)
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

	// métodos
	@Override
	public int hashCode() {
		return Objects.hash(fecha, parada, peregrino);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParadasPeregrino other = (ParadasPeregrino) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(parada, other.parada)
				&& Objects.equals(peregrino, other.peregrino);
	}

	@Override
	public String toString() {
		return "ParadasPeregrino [peregrino=" + peregrino + ", parada=" + parada + ", fecha=" + fecha + "]";
	}

}
