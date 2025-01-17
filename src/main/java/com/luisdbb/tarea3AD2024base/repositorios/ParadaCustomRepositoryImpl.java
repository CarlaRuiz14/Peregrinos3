package com.luisdbb.tarea3AD2024base.repositorios;

import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.QParada;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ParadaCustomRepositoryImpl implements ParadaCustomRepository{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override // para errore tipograficos a la hora de llamar al metodo
	public Parada findByIdUsuario(Long id) {

		QParada parada = QParada.parada;

		JPAQuery<Parada> query = new JPAQuery<>(entityManager);

		// condicion lógica para query
		BooleanExpression condicion = parada.usuario.id.eq(id);

		// devolver construccion y ejecucion (fecthOne - devuelve 1) de consulta
		return query.select(parada).from(parada).where(condicion).fetchOne();
	}

}
