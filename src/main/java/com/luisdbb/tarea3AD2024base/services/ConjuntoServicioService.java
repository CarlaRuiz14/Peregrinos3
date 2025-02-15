package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.controller.Alertas;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.repositorios.ConjuntoServicioRepository;

@Service
public class ConjuntoServicioService {

	
	@Autowired
	private ConjuntoServicioRepository csr;
	
	@Autowired
	private Alertas alertas;
	
	
	

	// METODOS CONJUNTOCONTRATADO

	// METODOS CONJUNTOSERVICIOS

	// METODOS SERVICIOS
	

	public void saveServicio(Servicio servicioNuevo) {
		csr.saveServicio(servicioNuevo);		
	}
	
	
	public List<Servicio> findAllServicios() {
		return csr.findAllServicios();
	}

	public Servicio crearServicioVacio() {		
		return new Servicio(0,"",0.0,null,null);		
	}


	// METODOS SECUENCIAID
	public void crearSecuenciaId() {
		csr.crearSecuenciaId();
	}	

}
