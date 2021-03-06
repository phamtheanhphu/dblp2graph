package com.dblp2graph.OGM.service.genetic;

interface OGMService<T> {

	public Iterable<T> findAll();

	public T find(Long id);

	public void delete(Long id);

	public T createOrUpdate(T object);

}
