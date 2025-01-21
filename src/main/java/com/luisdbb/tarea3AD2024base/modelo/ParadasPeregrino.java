package com.luisdbb.tarea3AD2024base.modelo;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

/**
 * Representa la relación entre un peregrino y una parada, permitiendo registrar
 * las visitas a paradas.
 * 
 * Atributos:
 * <ul>
 * <li><b>id:</b> Identificador compuesto que incluye el peregrino, la parada y
 * la fecha de la visita.</li>
 * <li><b>peregrino:</b> Referencia al peregrino que realizó la visita.</li>
 * <li><b>parada:</b> Referencia a la parada visitada.</li>
 * </ul>
 * 
 * Relaciones:
 * <ul>
 * <li><b>EmbeddedId:</b> ParadasPeregrinoId, que encapsula las claves de
 * peregrino, parada y fecha.</li>
 * <li><b>ManyToOne:</b> Relación con Peregrino.</li>
 * <li><b>ManyToOne:</b> Relación con Parada.</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */

@Entity
@Table(name = "paradas_peregrinos")

public class ParadasPeregrino {

	@EmbeddedId
	private ParadasPeregrinoId id;

	@ManyToOne
	@MapsId("idPeregrino")
	@JoinColumn(name = "id_peregrino", nullable = false)
	private Peregrino peregrino;

	@ManyToOne
	@MapsId("idParada")
	@JoinColumn(name = "id_parada", nullable = false)
	private Parada parada;

	public ParadasPeregrino() {
		super();
	}

	public ParadasPeregrino(Peregrino peregrino, Parada parada, LocalDate fecha) {
		super();
		this.id = new ParadasPeregrinoId(parada.getId(), peregrino.getId(), fecha);
		this.peregrino = peregrino;
		this.parada = parada;
	}

	public ParadasPeregrinoId getId() {
		return id;
	}

	public void setId(ParadasPeregrinoId id) {
		this.id = id;
	}

	public Peregrino getPeregrino() {
		return peregrino;
	}

	public void setPeregrino(Peregrino peregrino) {
		this.peregrino = peregrino;
	}

	public Parada getParada() {
		return parada;
	}

	public void setParada(Parada parada) {
		this.parada = parada;
	}

	public LocalDate getFecha() {
		return id.getFecha();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, parada, peregrino);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParadasPeregrino other = (ParadasPeregrino) obj;
		return Objects.equals(id, other.id) && Objects.equals(parada, other.parada)
				&& Objects.equals(peregrino, other.peregrino);
	}

	@Override
	public String toString() {
		return "ParadasPeregrino [id=" + id + ", peregrino=" + peregrino + ", parada=" + parada + "]";
	}
}
