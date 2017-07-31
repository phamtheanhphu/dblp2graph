package com.dblp2graph.OGM.service.impl;

import com.dblp2graph.OGM.entity.Publication;
import com.dblp2graph.OGM.service.PublicationService;
import com.dblp2graph.OGM.service.genetic.GenericService;

public class PublicationServiceImpl extends GenericService<Publication> implements PublicationService {

	public Iterable<Publication> findPubByDBLPKey(String dblpKey) {
		// TODO Auto-generated method stub
		String cypher = "MATCH (p:Publication {dblp_key:'" + dblpKey + "'}) RETURN p";
		return query(cypher);
	}

	public Publication findOneByDBLPKey(String dblpKey) {
		// TODO Auto-generated method stub
		String cypher = "MATCH (p:Publication {dblp_key:'" + dblpKey + "'}) RETURN p LIMIT 1";
		Iterable<Publication> pubs = this.query(cypher);
		return pubs.iterator().next();
	}

	@Override
	public Class<Publication> getEntityType() {
		// TODO Auto-generated method stub
		return Publication.class;
	}

}
