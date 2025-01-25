package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.QParada;
import com.luisdbb.tarea3AD2024base.modelo.QParadasPeregrino;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ParadaCustomRepositoryImpl implements ParadaCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;

	JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

	@Override
	public Parada findByIdUsuario(Long id) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

		
		QParada parada = QParada.parada;

		// devolver construccion y ejecucion (fecthOne - devuelve 1) de consulta
		return queryFactory
				.select(parada)
				.from(parada)
				.where(parada.usuario.id.eq(id))
				.fetchOne();
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