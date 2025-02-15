package com.luisdbb.tarea3AD2024base.modelo;

import java.util.Objects;

import org.springframework.stereotype.Component;
/**
 * Se guarda en db4o
 */
@Component
public class SecuenciaId {
	
	private long id;	
	
	public SecuenciaId() {
		super();
	}

	public SecuenciaId(long start) {
		super();
		this.id = start;
	}
	
	// para evitar comenzar en 0 la secuencia, primero aumenta y luego da la id
	public long nextId() {
		return ++id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SecuenciaId other = (SecuenciaId) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "SecuenciaId [id=" + id + "]";
	}	
	
	
	

}
