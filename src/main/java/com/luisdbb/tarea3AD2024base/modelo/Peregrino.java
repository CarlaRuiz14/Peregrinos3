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
@Table(name = "peregrinos")
public class Peregrino {

	// cascade = CascadeType.ALL

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nombre;
	private String nacionalidad;
	// relacion uno a uno con user
	@OneToOne (cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, unique = true) // Clave foránea a User, especificar unique y nullable, no implicito en FK
	private User usuario; // de User se saca la PK para la FK aqui y el nombre de la columna sera este
	// relacion uno a muchos con estancias
	@OneToMany(mappedBy = "peregrino", cascade = CascadeType.ALL) // Relación uno a muchos
	private List<Estancia> estancias;
	// relacion uno a uno con carnet
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, unique = true)
	private Carnet carnet;
	// relacion uno a muchos con las paradas de paradasperegrinos
//	@OneToMany(mappedBy = "idPeregrino") 
//	private List<ParadasPeregrinos> paradas;

}
