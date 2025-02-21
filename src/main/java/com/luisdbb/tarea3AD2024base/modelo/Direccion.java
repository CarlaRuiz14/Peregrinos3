package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class Direccion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String direccion;
	private String localidad;

}
