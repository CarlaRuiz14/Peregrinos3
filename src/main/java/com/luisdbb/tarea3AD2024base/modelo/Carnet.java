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
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Entity
@Table(name = "carnets")
public class Carnet {

	// sobra un constructor??

	// atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "fecha_expedicion", columnDefinition = "DATE DEFAULT CURRENT_DATE")
	private LocalDate fechaExp;

	@Column(name = "distancia", columnDefinition = "DOUBLE DEFAULT 0.0")
	private double distancia;

	@Column(name = "numero_vips", columnDefinition = "INT DEFAULT 0")
	private int nVips;
	
	//relacion bidireccional con peregrino pero solo mapeada, sin columna
	//cascada solo para persistir y actualizar no para borrar
	@OneToOne(mappedBy = "carnet", cascade = {CascadeType.PERSIST, CascadeType.MERGE})	
	private Peregrino peregrino;

	@ManyToOne //Una parada puede ser paradaInicial de varios carnets
	@JoinColumn(name = "parada_inicial", nullable = false)
	private Parada paradaInicial;

	// contructores
	public Carnet() {
		super();
	}

	public Carnet(Parada paradaInicial) {
		super();
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

	// getters y setters
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


	// Métodos
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
