package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Entity
@Table(name = "carnets")
public class Carnet {

	// atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private LocalDate fechaExp;

	private double distancia;

	private int nVips;

	@OneToOne
	@JoinColumn(nullable = false, unique = true)
	private Parada paradaInicial;

	// contructores
	public Carnet() {
		super();
	}

	public Carnet(long id, LocalDate fechaExp, double distancia, int nVips, Parada paradaInicial) {
		super();
		this.id = id;
		this.fechaExp = fechaExp;
		this.distancia = distancia;
		this.nVips = nVips;
		this.paradaInicial = paradaInicial;
	}

	// ??
	public Carnet(long id) {
		super();
		this.id = id;
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

	// Métodos
	@Override
	public String toString() {
	    return "Carnet{" +
	            "id=" + id +
	            ", fechaExp=" + fechaExp +
	            ", distancia=" + distancia +
	            ", nVips=" + nVips +
	            ", paradaInicial="+paradaInicial.getNombre()  +
	            '}';
	}


}
