package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
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
@Table(name = "estancias")
public class Estancia {

	// atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private LocalDate fecha;

	@Column(name = "vip",columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean vip;

	@ManyToOne
	@JoinColumn(name = "id_peregrino", nullable = false)
	private Peregrino peregrino;

	@ManyToOne
	@JoinColumn(name = "id_parada", nullable = false)
	private Parada parada;

	// constructores
	public Estancia() {
		super();
	}

	public Estancia(long id, LocalDate fecha, boolean vip) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.vip = vip;
	}

	public Estancia(long id, Peregrino peregrino, LocalDate fecha, boolean vip) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.vip = vip;
		this.peregrino = peregrino;
	}

	public Estancia(long id, LocalDate fecha, boolean vip, Peregrino peregrino, Parada parada) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.vip = vip;
		this.peregrino = peregrino;
		this.parada = parada;
	}

	// getters y setters
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public boolean getVip() {
		return vip;
	}

	public void setVip(boolean vip) {
		this.vip = vip;
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

	// métodos
	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, parada, peregrino, vip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estancia other = (Estancia) obj;
		return Objects.equals(fecha, other.fecha) && id == other.id && Objects.equals(parada, other.parada)
				&& Objects.equals(peregrino, other.peregrino) && Objects.equals(vip, other.vip);
	}

	@Override
	public String toString() {
		return "Estancia [id=" + id + ", fecha=" + fecha + ", vip=" + vip + ", peregrino=" + peregrino + ", parada="
				+ parada + "]";
	}

}
