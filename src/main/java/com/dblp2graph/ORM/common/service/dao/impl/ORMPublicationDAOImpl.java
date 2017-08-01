package com.dblp2graph.ORM.common.service.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.dblp2graph.ORM.common.entity.ORMPublication;
import com.dblp2graph.ORM.common.service.ORMGenericService;
import com.dblp2graph.ORM.common.service.dao.ORMPublicationDAO;

public class ORMPublicationDAOImpl extends ORMGenericService<ORMPublication> implements ORMPublicationDAO {

	private Session currentSession;
	private Transaction currentTransaction;

	public ORMPublicationDAOImpl() {
		currentSession = this.openCurrentSession();
	}

	public ORMPublication findByDBLPKey(String dblpKey) {
		Criteria criteria = this.currentSession.createCriteria(ORMPublication.class);
		return (ORMPublication) criteria.add(Restrictions.eq("dblpKey", dblpKey)).uniqueResult();
	}

	public void persist(ORMPublication entity) {
		// TODO Auto-generated method stub
	}

	public void update(ORMPublication entity) {
		// TODO Auto-generated method stub

	}

	public ORMPublication findPubById(int id) {
		// TODO Auto-generated method stub
		return (ORMPublication) this.currentSession.get(ORMPublication.class, id);
	}

	public void delete(ORMPublication entity) {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unchecked")
	public List<ORMPublication> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	public ORMPublication findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
