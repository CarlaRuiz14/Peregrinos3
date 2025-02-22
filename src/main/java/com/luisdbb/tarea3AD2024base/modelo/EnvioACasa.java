package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EnvioACasa extends Servicio implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private double peso;
	private double[] volumen;
	private boolean urgente = false;

	@Embedded
	private Direccion direccion;

	private long idParada;

	public EnvioACasa() {
		super();
	}

	public EnvioACasa(double peso, double[] volumen, boolean urgente, Direccion direccion, long idParada) {
		super();
		this.peso = peso;
		this.volumen = volumen;
		this.urgente = urgente;
		this.direccion = direccion;
		this.idParada = idParada;
	}

	public EnvioACasa(long id, double peso, double[] volumen, boolean urgente, Direccion direccion, long idParada) {
		super();
		this.id = id;
		this.peso = peso;
		this.volumen = volumen;
		this.urgente = urgente;
		this.direccion = direccion;
		this.idParada = idParada;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double[] getVolumen() {
		return volumen;
	}

	public void setVolumen(double[] volumen) {
		this.volumen = volumen;
	}

	public boolean isUrgente() {
		return urgente;
	}

	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public long getIdParada() {
		return idParada;
	}

	public void setIdParada(long idParada) {
		this.idParada = idParada;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(volumen);
		result = prime * result + Objects.hash(direccion, id, idParada, peso, urgente);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnvioACasa other = (EnvioACasa) obj;
		return Objects.equals(direccion, other.direccion) && id == other.id && idParada == other.idParada
				&& Double.doubleToLongBits(peso) == Double.doubleToLongBits(other.peso) && urgente == other.urgente
				&& Arrays.equals(volumen, other.volumen);
	}

	@Override
	public String toString() {
		return "EnvioACasa [id=" + id + ", peso=" + peso + ", volumen=" + Arrays.toString(volumen) + ", urgente="
				+ urgente + ", direccion=" + direccion + ", idParada=" + idParada + "]";
	}

}
