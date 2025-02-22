package com.luisdbb.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Servicio {

	private long id;
	private String nombre;
	private double precio;
	private List<Long> listaParadas;
	private List<Long> listaConjuntos;
	
	public Servicio() {		
		super();
	}
	
	public Servicio(long id, String nombre, double precio, Set<Long> listaParadas, Set<Long> listaConjuntos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.listaParadas = new ArrayList<>();
		this.listaConjuntos =  new ArrayList<>();
	}	
	
	public void addParada(long idParada) {
		listaParadas.add(idParada);	
	}
	
	public void addConjunto(long idConjunto) {
		listaParadas.add(idConjunto);	
	}	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	public List<Long> getListaParadas() {
		return listaParadas;
	}
	
	public void setListaParadas(List<Long> listaParadas) {
		this.listaParadas = listaParadas;
	}
	
	public List<Long> getListaConjuntos() {
		return listaConjuntos;
	}
	
	public void setListaConjuntos(List<Long> listaConjuntos) {
		this.listaConjuntos = listaConjuntos;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, listaConjuntos, listaParadas, nombre, precio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Servicio other = (Servicio) obj;
		return id == other.id && Objects.equals(listaConjuntos, other.listaConjuntos)
				&& Objects.equals(listaParadas, other.listaParadas) && Objects.equals(nombre, other.nombre)
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio);
	}

	@Override
	public String toString() {
		return "Servicio [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", listaParadas=" + listaParadas
				+ ", listaConjuntos=" + listaConjuntos + "]";
	}
}
