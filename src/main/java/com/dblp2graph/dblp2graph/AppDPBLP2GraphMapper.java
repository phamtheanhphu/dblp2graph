package com.dblp2graph.dblp2graph;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.neo4j.ogm.transaction.Transaction;

import com.dblp2graph.OGM.entity.Author;
import com.dblp2graph.OGM.factory.Neo4jSessionFactory;
import com.dblp2graph.common.Publication;
import com.dblp2graph.persistence.HibernateUtil;

public class AppDPBLP2GraphMapper {

	// private static org.neo4j.ogm.session.Session neo4JSession =
	// Neo4jSessionFactory.getInstance().getNeo4jSession();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		mapPubFromRDBMS2Graph();
	}

	private static void mapPubFromRDBMS2Graph() {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("from Publication");

		query.setFirstResult(0);
		query.setMaxResults(100000);

		List<Publication> pubs = query.list();
		org.neo4j.ogm.session.Session neo4JSession = Neo4jSessionFactory.getInstance().getNeo4jSession();
		Transaction tx = neo4JSession.beginTransaction();

		for (Publication pub : pubs) {

			if (pub != null) {
				
				//System.out.println(pub.toString());
				com.dblp2graph.OGM.entity.Publication pubOGM = new com.dblp2graph.OGM.entity.Publication();

				pubOGM = pub.toOGMObject();

				if (pubOGM != null) {
					try {
						neo4JSession.save(pubOGM);
					} catch (Exception e) {
						continue;
					}
				}
			}
		}
		
		tx.commit();

	}

}
