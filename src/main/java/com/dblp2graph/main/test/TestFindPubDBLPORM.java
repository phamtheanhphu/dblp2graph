package com.dblp2graph.main.test;

import com.dblp2graph.ORM.common.entity.ORMPublication;
import com.dblp2graph.ORM.common.service.dao.ORMAuthorDAO;
import com.dblp2graph.ORM.common.service.dao.ORMPublicationDAO;
import com.dblp2graph.ORM.common.service.dao.impl.ORMAuthorDAOImpl;
import com.dblp2graph.ORM.common.service.dao.impl.ORMPublicationDAOImpl;

public class TestFindPubDBLPORM {

	// RDBMS
	private static ORMAuthorDAO ormAuthorDAO = new ORMAuthorDAOImpl();
	private static ORMPublicationDAO ormPublicationDAO = new ORMPublicationDAOImpl();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String title = "Measuring e-government impact: existing practices and shortcomings.";
		ORMPublication pubORM = ormPublicationDAO.findByTitle(title);
		if (pubORM != null) {
			System.out.println(pubORM.getTitle());
		}

	}

}
