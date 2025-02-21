package com.luisdbb.tarea3AD2024base.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;
import com.luisdbb.tarea3AD2024base.repositorios.ConjuntoServicioRepository;

@Service
public class ConjuntoServicioService {

	@Autowired
	private ConjuntoServicioRepository csr;

	// METODOS CONJUNTOCONTRATADO

	public void saveConjunto(ConjuntoContratado conjuntoNuevo) {
		try {
			csr.saveConjunto(conjuntoNuevo);
		} catch (Exception e) {
			throw new RuntimeException("Error al guardar el conjunto", e);
		}
	}

	// METODOS SERVICIOS

	public boolean existeNombreServicio(String nombre) {
		return csr.existeNombreServicio(nombre);
	}

	public void saveServicio(Servicio servicioNuevo) {
		try {
			csr.saveServicio(servicioNuevo);
		} catch (Exception e) {
			throw new RuntimeException("Error al guardar el servicio", e);
		}
	}

	public Set<Servicio> findAllServicios() {
		return csr.findAllServicios();
	}

	public Servicio crearServicioVacio() {
		return new Servicio(0, "", 0.0, null, null);
	}
	
	public Set<Servicio> findServiciosByIdParada(long idParada){
		return csr.findServiciosByIdParada(idParada);
	}
	
	public Servicio getServicioByName(String nombre) {
		return csr.getServicioByName(nombre);
	}

}
