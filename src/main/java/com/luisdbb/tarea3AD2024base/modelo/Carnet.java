package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Representa el carnet de un peregrino, incluyendo su fecha de expedición,
 * distancia recorrida, estancias VIP y parada inicial.
 * 
 * Atributos:
 * <ul>
 * <li><b>id:</b> Identificador único.</li>
 * <li><b>fechaExp:</b> Fecha de expedición.</li>
 * <li><b>distancia:</b> Distancia total recorrida.</li>
 * <li><b>nVips:</b> Número de estancias VIP.</li>
 * <li><b>peregrino:</b> Relación con Peregrino.</li>
 * <li><b>paradaInicial:</b> Parada de inicio (relación ManyToOne).</li>
 * </ul>
 * 
 * Relaciones:
 * <ul>
 * <li><b>OneToOne:</b> Peregrino (cascada PERSIST, MERGE).</li>
 * <li><b>ManyToOne:</b> Parada inicial (obligatoria).</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */

@Entity
@Table(name = "carnets")
public class Carnet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "fecha_expedicion")
	private LocalDate fechaExp;

	@Column(name = "distancia")
	private double distancia;

	@Column(name = "numero_vips")
	private int nVips;

	@OneToOne(mappedBy = "carnet", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Peregrino peregrino;

	@ManyToOne
	@JoinColumn(name = "parada_inicial", nullable = false)
	private Parada paradaInicial;

	public Carnet() {
		super();
	}

	public Carnet(Parada paradaInicial) {
		super();
		this.fechaExp = LocalDate.now();
		this.distancia = 0.0;
		this.nVips = 0;
		this.paradaInicial = paradaInicial;
	}

	public Carnet(long id, LocalDate fechaExp, double distancia, int nVips, Parada paradaInicial) {
		super();
		this.id = id;
		this.fechaExp = fechaExp;
		this.distancia = distancia;
		this.nVips = nVips;
		this.paradaInicial = paradaInicial;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getFechaExp() {
		return fechaExp;
	}

	public void setFechaExp(LocalDate fechaExp) {
		this.fechaExp = fechaExp;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public int getnVips() {
		return nVips;
	}

	public void setnVips(int nVips) {
		this.nVips = nVips;
	}

	public Parada getParadaInicial() {
		return paradaInicial;
	}

	public void setParadaInicial(Parada paradaInicial) {
		this.paradaInicial = paradaInicial;
	}

	public Peregrino getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(distancia, fechaExp, id, nVips, paradaInicial, peregrino);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carnet other = (Carnet) obj;
		return Double.doubleToLongBits(distancia) == Double.doubleToLongBits(other.distancia)
				&& Objects.equals(fechaExp, other.fechaExp) && id == other.id && nVips == other.nVips
				&& Objects.equals(paradaInicial, other.paradaInicial) && Objects.equals(peregrino, other.peregrino);
	}

	@Override
	public String toString() {
		return "Carnet [id=" + id + ", fechaExp=" + fechaExp + ", distancia=" + distancia + ", nVips=" + nVips
				+ ", peregrino=" + peregrino + ", paradaInicial=" + paradaInicial + "]";
	}
}
