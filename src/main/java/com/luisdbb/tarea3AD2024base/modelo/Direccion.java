package com.luisdbb.tarea3AD2024base.modelo;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

/**
 * Representa una dirección con información de dirección y localidad.
 * 
 * Atributos:
 * <ul>
 * <li><b>direccion:</b> Calle y número de la dirección.</li>
 * <li><b>localidad:</b> Ciudad o localidad donde se encuentra la
 * dirección.</li>
 * </ul>
 *
 * La clase es embeddable, por lo que puede ser usada dentro de otras
 * entidades en JPA sin ser una entidad independiente.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Embeddable
public class Direccion implements Serializable {

	private static final long serialVersionUID = 1L;

	private String direccion;
	private String localidad;

	public Direccion() {
	}

	public Direccion(String direccion, String localidad) {
		this.direccion = direccion;
		this.localidad = localidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(direccion, localidad);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Direccion other = (Direccion) obj;
		return Objects.equals(direccion, other.direccion) && Objects.equals(localidad, other.localidad);
	}

	@Override
	public String toString() {
		return "Direccion [direccion=" + direccion + ", localidad=" + localidad + "]";
	}

}
