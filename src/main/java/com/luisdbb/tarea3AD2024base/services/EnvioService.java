package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.repositorios.EnvioRepository;

/**
 * Servicio para la gestión de envíos a domicilio.
 *
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class EnvioService {

	@Autowired
	private EnvioRepository envioRepository;

	/**
	 * Guarda un nuevo envío en la base de datos.
	 * 
	 * @param envio Envío a registrar.
	 * @return Envío guardado con su ID asignado.
	 */
	public EnvioACasa saveEnvio(EnvioACasa envio) {
		return envioRepository.saveEnvio(envio);
	}

	/**
	 * Obtiene la lista de envíos asociados a una parada específica.
	 * 
	 * @param idParada Identificador de la parada.
	 * @return Lista de envíos registrados en la parada.
	 */
	public List<EnvioACasa> getEnvios(long idParada) {
		return envioRepository.getEnvios(idParada);
	}

}
