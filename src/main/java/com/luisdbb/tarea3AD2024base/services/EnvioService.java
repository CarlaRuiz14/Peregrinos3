package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.repositorios.EnvioRepository;

@Service
public class EnvioService {
	
	@Autowired
	private EnvioRepository envioRepository;
	
	public EnvioACasa saveEnvio(EnvioACasa envio) {		
		return envioRepository.saveEnvio(envio);
	}
}
