package com.luisdbb.tarea3AD2024base.modelo;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Entity
@Table(name = "paradas")
public class Parada {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nombre;
	private char region;
	
	@OneToOne (cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, unique = true)
	private User usuario;

	@OneToMany(mappedBy = "parada")
	private List<Estancia> estancias;
	
	// relacion uno a muchos con los peregrinos de paradasperegrinos
//	@OneToMany(mappedBy = "idParada") 
//	private List<ParadasPeregrinos> peregrinos;
}
