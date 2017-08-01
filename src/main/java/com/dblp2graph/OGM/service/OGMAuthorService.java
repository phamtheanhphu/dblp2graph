package com.dblp2graph.OGM.service;

import com.dblp2graph.OGM.entity.OGMAuthor;

public interface OGMAuthorService {

	public Iterable<OGMAuthor> findAuthorByName(String authorName);

	public void save(OGMAuthor author);
	
}
