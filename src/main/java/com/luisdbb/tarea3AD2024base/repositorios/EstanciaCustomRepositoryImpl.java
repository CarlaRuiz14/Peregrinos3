package com.luisdbb.tarea3AD2024base.repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.QEstancia;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class EstanciaCustomRepositoryImpl implements EstanciaCustomRepository {

	// punto de entrada para BD
	@PersistenceContext
	private EntityManager entityManager;

	@Override // para errore tipograficos a la hora de llamar al metodo
	public List<Estancia> findByIdParadaAndFechaBetween(Long paradaId, LocalDate fechaInicio, LocalDate fechaFin) {

		QEstancia estancia = QEstancia.estancia;

		JPAQuery<Estancia> query = new JPAQuery<>(entityManager);

		// condicion lógica para query
		BooleanExpression condicion = estancia.parada.id.eq(paradaId)
				.and(estancia.fecha.between(fechaInicio, fechaFin));

		// devolver construccion y ejecucion (fecth) de consulta
		return query.select(estancia).from(estancia).where(condicion).fetch();
	}

}
