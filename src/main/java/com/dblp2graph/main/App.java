package com.dblp2graph.main;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dblp2graph.ORM.common.entity.ORMAuthor;
import com.dblp2graph.ORM.common.entity.ORMPublication;
import com.dblp2graph.ORM.common.service.dao.ORMPublicationDAO;
import com.dblp2graph.ORM.common.service.dao.impl.ORMPublicationDAOImpl;
import com.dblp2graph.ORM.persistence.HibernateUtil;

public class App {

	private static ORMPublicationDAO ormPublicationDAO = new ORMPublicationDAOImpl();;

	public static void main(String[] args) {
		// showAuthor();
		showPublish();
	}

	private static void showPublish() {
		ORMPublication ormPub = ormPublicationDAO.findByDBLPKey("journals/acta/Saxena96");
		System.out.println(ormPub.toString());
	}

	private static void showAuthor() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Author where author = :author");

		query.setParameter("author", "Rakesh Agrawal");

		List<ORMAuthor> authors = query.list();

		for (ORMAuthor author : authors) {
			System.out.println(author.toString());
			Iterator<ORMPublication> it = author.getAuthorOfPubs().iterator();
			System.out.println("---------------");
			while (it.hasNext()) {
				ORMPublication publish = it.next();
				System.out.println(publish.toString());
				System.out.println("Total references -> [" + publish.getReferPublishes().size() + "]");
				System.out.println();
			}
		}

	}

}
