package com.dblp2graph.dblp2graph;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dblp2graph.common.Author;
import com.dblp2graph.common.Publication;
import com.dblp2graph.common.publication.PublicationData;
import com.dblp2graph.persistence.HibernateUtil;

public class App {

	public static void main(String[] args) {
		showAuthor();
	}
	
	private static void showPublish() {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Publish where id = :id");
		query.setParameter("id", 3566628);
		List<Publication> publishes = query.list();
		
		for (Publication publish : publishes) {
			System.out.println(publish.toString());
			System.out.println("Total references -> [" + publish.getCitedPublishes().size() + "]");
		}
		
	}
	
	private static void showAuthor() {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Author where author = :author");

		query.setParameter("author", "Rakesh Agrawal");

		List<Author> authors = query.list();

		for (Author author : authors) {
			System.out.println(author.toString());
			Iterator<Publication> it = author.getPublishes().iterator();
			System.out.println("---------------");
			while (it.hasNext()) {
				Publication publish = it.next();
				System.out.println(publish.toString());
				System.out.println("Total references -> [" + publish.getReferPublishes().size() + "]");
				System.out.println();
			}
		}
		
	}
	
}
