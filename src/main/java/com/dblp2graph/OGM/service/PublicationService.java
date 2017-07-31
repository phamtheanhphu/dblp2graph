package com.dblp2graph.OGM.service;

import com.dblp2graph.OGM.entity.Publication;

public interface PublicationService {

	public Iterable<Publication> findPubByDBLPKey(String dblpKey);
	
	public Publication findOneByDBLPKey(String dblpKey);

}
