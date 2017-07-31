package com.dblp2graph.dblp2graph;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.neo4j.ogm.transaction.Transaction;

import com.dblp2graph.OGM.entity.Author;
import com.dblp2graph.OGM.factory.Neo4jSessionFactory;
import com.dblp2graph.OGM.service.PublicationService;
import com.dblp2graph.OGM.service.impl.PublicationServiceImpl;
import com.dblp2graph.common.Publication;
import com.dblp2graph.persistence.HibernateUtil;

public class AppDPBLP2GraphMapper {

	private static PublicationService publicationService = new PublicationServiceImpl();
	// private static org.neo4j.ogm.session.Session neo4JSession =
	// Neo4jSessionFactory.getInstance().getNeo4jSession();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		mapPubFromRDBMS2Graph();
		// fetchAllPubIdList();
	}

	private static void mapPubFromRDBMS2Graph() {

		Session session = HibernateUtil.getSessionFactory().openSession();

		org.neo4j.ogm.session.Session neo4JSession = Neo4jSessionFactory.getInstance().getNeo4jSession();
		Transaction tx = neo4JSession.beginTransaction();

		List<Integer> pubIds = fetchAllPubIdList();

		for (int pubId : pubIds) {

			Query query = session.createQuery("from Publication where id = :id");
			query.setParameter("id", pubId);
			List<Publication> pubs = query.list();

			Publication pubORM = pubs.get(0);

			if (pubORM != null) {

				System.out.println(pubORM.getDblpKey());
				com.dblp2graph.OGM.entity.Publication pubOGM = new com.dblp2graph.OGM.entity.Publication();

				pubOGM = pubORM.toOGMObject();

				if (pubOGM != null) {

					try {

						// add refer to relation
						Set<Publication> referToPubsORM = pubORM.getReferPublishes();
						// System.out.println(referToPubsORM.size());
						if (referToPubsORM != null) {
							for (Publication referToPubORM : referToPubsORM) {
								// System.out.println(referToPubORM.toString());
								pubOGM.getReferToPubs().add(referToPubORM.toOGMObject());
							}
						}

						// add cited by relation
						Set<Publication> citedByPubsORM = pubORM.getCitedPublishes();
						if (citedByPubsORM != null) {
							for (Publication citedByPubORM : citedByPubsORM) {
								pubOGM.getCitedPubs().add(citedByPubORM.toOGMObject());
							}
						}

						neo4JSession.save(pubOGM);
						tx.commit();

					} catch (Exception e) {
						continue;
					}
				}
			}
		}

	}

	private static List<Integer> fetchAllPubIdList() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("select id from Publication");
		List<Integer> pubIds = query.list();
		System.out.println("Fetching total records -> " + pubIds.size());
		return pubIds;
	}

}
