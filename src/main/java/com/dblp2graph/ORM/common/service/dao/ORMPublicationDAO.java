package com.dblp2graph.ORM.common.service.dao;

import java.io.Serializable;

import com.dblp2graph.ORM.common.entity.ORMPublication;

public interface ORMPublicationDAO extends ORMGenericDAO<ORMPublication, String> {

	public ORMPublication findByDBLPKey(String dblpKey);

	public ORMPublication findPubById(int id);

}
