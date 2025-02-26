package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Representa un conjunto de servicios contratados en una estancia, incluyendo
 * su precio total, método de pago y servicios asociados.
 * 
 * Atributos:
 * <ul>
 * <li><b>id:</b> Identificador único del conjunto contratado.</li>
 * <li><b>precioTotal:</b> Precio total de los servicios contratados.</li>
 * <li><b>modoPago:</b> Método de pago utilizado.</li>
 * <li><b>extra:</b> Información adicional sobre el servicio contratado.</li>
 * <li><b>idEstancia:</b> Identificador de la estancia asociada a los
 * servicios.</li>
 * <li><b>listaServicios:</b> Lista de identificadores de servicios
 * contratados.</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
public class ConjuntoContratado {

	private long id;
	private double precioTotal;
	private char modoPago;
	private String extra;
	private long idEstancia;
	private List<Long> listaServicios;

	public ConjuntoContratado() {
		super();
	}

	public ConjuntoContratado(long id, double precioTotal, char modoPago, long idEstancia, Set<Long> listaServicios) {
		super();
		this.id = id;
		this.precioTotal = precioTotal;
		this.modoPago = modoPago;
		this.idEstancia = idEstancia;
		this.listaServicios = new ArrayList<>();
	}

	public ConjuntoContratado(long id, double precioTotal, char modoPago, String extra, long idEstancia,
			Set<Long> listaServicios) {
		super();
		this.id = id;
		this.precioTotal = precioTotal;
		this.modoPago = modoPago;
		this.extra = extra;
		this.idEstancia = idEstancia;
		this.listaServicios = new ArrayList<>(listaServicios);
	}

	public void addServicio(long idServicio) {
		listaServicios.add(idServicio);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public char getModoPago() {
		return modoPago;
	}

	public void setModoPago(char modoPago) {
		this.modoPago = modoPago;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public long getIdEstancia() {
		return idEstancia;
	}

	public void setIdEstancia(long idEstancia) {
		this.idEstancia = idEstancia;
	}

	public List<Long> getListaServicios() {
		return listaServicios;
	}

	public void setListaServicios(List<Long> listaServicios) {
		this.listaServicios = listaServicios;
	}

	@Override
	public int hashCode() {
		return Objects.hash(extra, id, idEstancia, listaServicios, modoPago, precioTotal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConjuntoContratado other = (ConjuntoContratado) obj;
		return Objects.equals(extra, other.extra) && id == other.id && idEstancia == other.idEstancia
				&& Objects.equals(listaServicios, other.listaServicios) && modoPago == other.modoPago
				&& Double.doubleToLongBits(precioTotal) == Double.doubleToLongBits(other.precioTotal);
	}

	@Override
	public String toString() {
		return "ConjuntoContratado [id=" + id + ", precioTotal=" + precioTotal + ", modoPago=" + modoPago + ", extra="
				+ extra + ", idEstancia=" + idEstancia + ", listaServicios=" + listaServicios + "]";
	}
}
