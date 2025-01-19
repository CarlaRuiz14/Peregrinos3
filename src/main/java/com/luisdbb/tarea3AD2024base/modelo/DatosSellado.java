package com.luisdbb.tarea3AD2024base.modelo;

import org.springframework.stereotype.Component;

@Component
public class DatosSellado {

	private Peregrino peregrino;
	private Parada parada;

	public DatosSellado() {
		super();
	}

	public DatosSellado(Peregrino peregrino, Parada parada) {
		super();
		this.peregrino = peregrino;
		this.parada = parada;
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

}
