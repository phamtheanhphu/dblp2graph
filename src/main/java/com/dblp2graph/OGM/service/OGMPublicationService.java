package com.dblp2graph.OGM.service;

import com.dblp2graph.OGM.entity.OGMPublication;

public interface OGMPublicationService {

	public Iterable<OGMPublication> findPubByDBLPKey(String dblpKey);

	public OGMPublication findOneByDBLPKey(String dblpKey);

	public void save(OGMPublication pub);

}
