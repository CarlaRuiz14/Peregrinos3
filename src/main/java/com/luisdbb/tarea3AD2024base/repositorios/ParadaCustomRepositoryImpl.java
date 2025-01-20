package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.QParada;
import com.luisdbb.tarea3AD2024base.modelo.QParadasPeregrino;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ParadaCustomRepositoryImpl implements ParadaCustomRepository{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Parada findByIdUsuario(Long id) {

		QParada parada = QParada.parada;

		JPAQuery<Parada> query = new JPAQuery<>(entityManager);

		// condicion lógica para query
		BooleanExpression condicion = parada.usuario.id.eq(id);

		// devolver construccion y ejecucion (fecthOne - devuelve 1) de consulta
		return query.select(parada).from(parada).where(condicion).fetchOne();
	}
	
	@Override
	public List<Parada> findParadasByPeregrinoId(Long peregrinoId) {
	    
	    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
	    
	    QParada parada = QParada.parada;
	    QParadasPeregrino paradasPeregrino = QParadasPeregrino.paradasPeregrino;

	    return queryFactory
	            .select(parada)
	            .from(parada)
	            .innerJoin(paradasPeregrino)
	            .on(parada.id.eq(paradasPeregrino.parada.id))// la columna comun
	            .where(paradasPeregrino.peregrino.id.eq(peregrinoId)) // filtro
	            .fetch(); 
	}

	

}
