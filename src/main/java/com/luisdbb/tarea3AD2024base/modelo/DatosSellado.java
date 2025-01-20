package com.luisdbb.tarea3AD2024base.modelo;

import org.springframework.stereotype.Component;

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
