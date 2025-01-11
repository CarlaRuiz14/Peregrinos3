package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.Objects;

public class ParadasPeregrinosId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idPeregrino;
	private Long idParada;

	public ParadasPeregrinosId() {
		super();
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

	@Override
	public int hashCode() {
		return Objects.hash(idParada, idPeregrino);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParadasPeregrinosId other = (ParadasPeregrinosId) obj;
		return Objects.equals(idParada, other.idParada) && Objects.equals(idPeregrino, other.idPeregrino);
	}

}
