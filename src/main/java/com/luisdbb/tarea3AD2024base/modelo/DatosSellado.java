package com.luisdbb.tarea3AD2024base.modelo;

import org.springframework.stereotype.Component;

/**
 * Representa los datos necesarios para realizar el sellado de un carnet,
 * incluyendo el peregrino, la parada y el carnet asociado.
 * 
 * Atributos:
 * <ul>
 * <li><b>peregrino:</b> Peregrino que realiza el sellado.</li>
 * <li><b>parada:</b> Parada en la que se realiza el sellado.</li>
 * <li><b>carnet:</b> Carnet del peregrino.</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */

@Component
public class DatosSellado {

	private Peregrino peregrino;
	private Parada parada;
	private Carnet carnet;

	public DatosSellado() {
		super();
	}

	public DatosSellado(Peregrino peregrino, Parada parada, Carnet carnet) {
		super();
		this.peregrino = peregrino;
		this.parada = parada;
		this.carnet = carnet;
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

	public Carnet getCarnet() {
		return carnet;
	}

	public void setCarnet(Carnet carnet) {
		this.carnet = carnet;
	}
}
