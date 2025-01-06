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
@Table(name = "estancias")
public class Estancia {
	
	//corregir constructor y getters y setters
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private LocalDate fecha;
	private Boolean vip;
	
	@ManyToOne 
	@JoinColumn(nullable = false) 
	private Peregrino peregrino;
	
	@ManyToOne 
	@JoinColumn(nullable = false) 
	private Parada parada;

	public Estancia(long id, LocalDate fecha, Boolean vip) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.vip = vip;
	}	
	
	public Estancia(long id,Peregrino peregrino, LocalDate fecha, Boolean vip) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.vip = vip;
		this.peregrino = peregrino;		
	}

	public Estancia(long id, LocalDate fecha, Boolean vip, Peregrino peregrino, Parada parada) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.vip = vip;
		this.peregrino = peregrino;
		this.parada = parada;
	}



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

	public Parada getParada() {
		return parada;
	}

	public void setParada(Parada parada) {
		this.parada = parada;
	}

	public Boolean getVip() {
		return vip;
	}

	public void setVip(Boolean vip) {
		this.vip = vip;
	}

	public Peregrino getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	
	
	
	
}
