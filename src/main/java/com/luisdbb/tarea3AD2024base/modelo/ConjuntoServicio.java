package com.luisdbb.tarea3AD2024base.modelo;

import java.util.Objects;

public class ConjuntoServicio {

	private long idConjuntoContratado;
	private long idServicio;
	
	public ConjuntoServicio() {
		super();
	}
	
	public ConjuntoServicio(long idConjuntoContratado, long idServicio) {
		super();
		this.idConjuntoContratado = idConjuntoContratado;
		this.idServicio = idServicio;
	}
	
	public long getIdConjuntoContratado() {
		return idConjuntoContratado;
	}
	
	public void setIdConjuntoContratado(long idConjuntoContratado) {
		this.idConjuntoContratado = idConjuntoContratado;
	}
	
	public long getIdServicio() {
		return idServicio;
	}
	
	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idConjuntoContratado, idServicio);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConjuntoServicio other = (ConjuntoServicio) obj;
		return idConjuntoContratado == other.idConjuntoContratado && idServicio == other.idServicio;
	}
	
	@Override
	public String toString() {
		return "ConjuntoServicio [idConjuntoContratado=" + idConjuntoContratado + ", idServicio=" + idServicio + "]";
	}
}
