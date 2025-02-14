package com.luisdbb.tarea3AD2024base.modelo;

import org.springframework.stereotype.Component;

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

}
