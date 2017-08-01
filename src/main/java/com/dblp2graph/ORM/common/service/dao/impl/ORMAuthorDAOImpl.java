package com.dblp2graph.ORM.common.service.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.dblp2graph.ORM.common.entity.ORMAuthor;
import com.dblp2graph.ORM.common.entity.ORMPublication;
import com.dblp2graph.ORM.common.service.ORMGenericService;
import com.dblp2graph.ORM.common.service.dao.ORMAuthorDAO;

public class ORMAuthorDAOImpl extends ORMGenericService<ORMAuthor> implements ORMAuthorDAO {

	private Session currentSession;
	private Transaction currentTransaction;

	public ORMAuthorDAOImpl() {
		currentSession = this.openCurrentSession();
	}

	public ORMAuthor findAuthorById(int authorId) {
		// TODO Auto-generated method stub
		return (ORMAuthor) this.currentSession.get(ORMAuthor.class, authorId);
	}

	public ORMAuthor findByName(String authorName) {
		// TODO Auto-generated method stub
		Criteria criteria = this.currentSession.createCriteria(ORMAuthor.class);
		return (ORMAuthor) criteria.add(Restrictions.eq("author", authorName)).uniqueResult();

	}

	public List<ORMAuthor> fetchWithLimit(int startAt, int maxTakenRecordNum) {
		// TODO Auto-generated method stub
		Criteria criteria = this.currentSession.createCriteria(ORMAuthor.class);
		return criteria.setFirstResult(startAt).setMaxResults(maxTakenRecordNum).list();
	}

	public void persist(ORMAuthor entity) {
		// TODO Auto-generated method stub
	}

	public void update(ORMAuthor entity) {
		// TODO Auto-generated method stub

	}

	public ORMAuthor findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(ORMAuthor entity) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public List<ORMAuthor> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteAll() {
		// TODO Auto-generated method stub

	}

}
