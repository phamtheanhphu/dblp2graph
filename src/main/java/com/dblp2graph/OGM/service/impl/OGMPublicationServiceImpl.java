package com.dblp2graph.OGM.service.impl;

import com.dblp2graph.OGM.entity.OGMPublication;
import com.dblp2graph.OGM.service.OGMPublicationService;
import com.dblp2graph.OGM.service.genetic.OGMGenericService;

public class OGMPublicationServiceImpl extends OGMGenericService<OGMPublication> implements OGMPublicationService {
	
	@Override
	public void save(OGMPublication entity) {
		// TODO Auto-generated method stub
		super.save(entity);
	}
	
	public Iterable<OGMPublication> findPubByDBLPKey(String dblpKey) {
		// TODO Auto-generated method stub
		String cypher = "MATCH (p:Publication {dblp_key:'" + dblpKey + "'}) RETURN p";
		return query(cypher);
	}

	public OGMPublication findOneByDBLPKey(String dblpKey) {
		// TODO Auto-generated method stub
		String cypher = "MATCH (p:Publication {dblp_key:'" + dblpKey + "'}) RETURN p LIMIT 1";
		Iterable<OGMPublication> pubs = this.query(cypher);
		return pubs.iterator().next();
	}

	@Override
	public Class<OGMPublication> getEntityType() {
		// TODO Auto-generated method stub
		return OGMPublication.class;
	}

}
