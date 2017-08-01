package com.dblp2graph.OGM.service.impl;

import com.dblp2graph.OGM.entity.OGMAuthor;
import com.dblp2graph.OGM.entity.OGMPublication;
import com.dblp2graph.OGM.service.OGMAuthorService;
import com.dblp2graph.OGM.service.OGMPublicationService;
import com.dblp2graph.OGM.service.genetic.OGMGenericService;

public class OGMAuthorServiceImpl extends OGMGenericService<OGMAuthor> implements OGMAuthorService {

	public Iterable<OGMAuthor> findAuthorByName(String authorName) {
		// TODO Auto-generated method stub
		String cypher = "MATCH (a:Author {author:'" + authorName + "'}) RETURN a";
		return query(cypher);
	}

	@Override
	public void save(OGMAuthor author) {
		// TODO Auto-generated method stub
		super.save(author);
	}

	@Override
	protected Class<OGMAuthor> getEntityType() {
		// TODO Auto-generated method stub
		return null;
	}

}
