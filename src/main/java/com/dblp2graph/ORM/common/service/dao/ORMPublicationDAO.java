package com.dblp2graph.ORM.common.service.dao;

import com.dblp2graph.ORM.common.entity.ORMPublication;

public interface ORMPublicationDAO extends ORMGenericDAO<ORMPublication, String> {

	public ORMPublication findByDBLPKey(String dblpKey);

	public ORMPublication findByTitle(String title);
	
	public ORMPublication findPubById(int id);

}
