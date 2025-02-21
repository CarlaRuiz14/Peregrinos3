package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class EnvioACasa extends Servicio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue
	private long id;
	private double peso;
	private double[] volumen;
	private boolean urgente=false;
	
	@Embedded
	private Direccion direccion;
	
	private long idParada;
	
	

}
