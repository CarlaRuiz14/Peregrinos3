package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "estancias")
public class Estancia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private LocalDate fecha;
	private boolean vip;
	
	@ManyToOne 
	@JoinColumn(nullable = false) 
	private Peregrino peregrino;
	
	@ManyToOne 
	@JoinColumn(nullable = false) 
	private Parada parada;
}
