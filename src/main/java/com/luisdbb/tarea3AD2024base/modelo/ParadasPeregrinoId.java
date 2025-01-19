package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class ParadasPeregrinoId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPeregrino;
	private Long idParada;
	private LocalDate fecha;

	public ParadasPeregrinoId() {
		super();
	}

	public ParadasPeregrinoId(Long idPeregrino, Long idParada, LocalDate fecha) {
		super();
		this.idPeregrino = idPeregrino;
		this.idParada = idParada;
		this.fecha = fecha;
	}

	public Long getIdPeregrino() {
		return idPeregrino;
	}

	public void setIdPeregrino(Long idPeregrino) {
		this.idPeregrino = idPeregrino;
	}

	public Long getIdParada() {
		return idParada;
	}

	public void setIdParada(Long idParada) {
		this.idParada = idParada;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, idParada, idPeregrino);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParadasPeregrinoId other = (ParadasPeregrinoId) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(idParada, other.idParada)
				&& Objects.equals(idPeregrino, other.idPeregrino);
	}

}
