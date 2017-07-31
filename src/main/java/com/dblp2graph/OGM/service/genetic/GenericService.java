package com.dblp2graph.OGM.service.genetic;

import java.util.Collections;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import com.dblp2graph.OGM.factory.Neo4jSessionFactory;

public abstract class GenericService<T> implements Service<T> {

	private static final int DEPTH_LIST = 0;
	private static final int DEPTH_ENTITY = 1;
	protected Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

	public Iterable<T> findAll() {
		return session.loadAll(getEntityType(), DEPTH_LIST);
	}

	public T find(Long id) {
		return session.load(getEntityType(), id, DEPTH_ENTITY);
	}

	public Iterable<T> query(String cypher) {
		return session.query(getEntityType(), cypher, Collections.EMPTY_MAP);
	}
	
	public void save(T entity) {
		Transaction tx = session.beginTransaction();
		session.save(entity);
		tx.commit();
	}
	
	public void delete(Long id) {
		session.delete(session.load(getEntityType(), id));
	}

	public T createOrUpdate(T entity) {
		session.save(entity, DEPTH_ENTITY);
		return entity;
	}

	protected abstract Class<T> getEntityType();

}
