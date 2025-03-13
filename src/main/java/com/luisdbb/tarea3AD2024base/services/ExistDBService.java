package com.luisdbb.tarea3AD2024base.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.repositorios.ExistDBRepository;

@Service
public class ExistDBService {

	@Autowired
	private ExistDBRepository existdbRespository;
	
	public void crearColeccionEnParadas(String nombreParada) throws Exception {
		existdbRespository.crearColeccionEnParadas(nombreParada);
	}
	
	public void almacenarCarnet(String paradaInicial, File carnetXml) throws Exception {
		existdbRespository.almacenarCarnet(paradaInicial, carnetXml);
	}
	
	public String[] listarCarnets(String nombreParada) throws Exception {
		return existdbRespository.listarCarnets(nombreParada);
	}
}
