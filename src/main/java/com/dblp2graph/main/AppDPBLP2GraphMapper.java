package com.dblp2graph.main;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.neo4j.ogm.transaction.Transaction;

import com.dblp2graph.OGM.entity.OGMAuthor;
import com.dblp2graph.OGM.factory.Neo4jSessionFactory;
import com.dblp2graph.OGM.service.OGMAuthorService;
import com.dblp2graph.OGM.service.OGMPublicationService;
import com.dblp2graph.OGM.service.impl.OGMAuthorServiceImpl;
import com.dblp2graph.OGM.service.impl.OGMPublicationServiceImpl;
import com.dblp2graph.ORM.common.entity.ORMAuthor;
import com.dblp2graph.ORM.common.entity.ORMPublication;
import com.dblp2graph.ORM.common.service.dao.ORMAuthorDAO;
import com.dblp2graph.ORM.common.service.dao.ORMPublicationDAO;
import com.dblp2graph.ORM.common.service.dao.impl.ORMAuthorDAOImpl;
import com.dblp2graph.ORM.common.service.dao.impl.ORMPublicationDAOImpl;
import com.dblp2graph.ORM.persistence.HibernateUtil;

public class AppDPBLP2GraphMapper {

	// RDBMS
	private static ORMAuthorDAO ormAuthorDAO = new ORMAuthorDAOImpl();
	private static ORMPublicationDAO ormPublicationDAO = new ORMPublicationDAOImpl();

	// Graph
	private static OGMPublicationService ogmPublicationService = new OGMPublicationServiceImpl();
	private static OGMAuthorService ogmAuthorService = new OGMAuthorServiceImpl();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// mapPubFromRDBMS2Graph();
		// fetchAllPubIdList();
		mapAuthorFromRDBMS2Graph();
	}

	private static void mapAuthorFromRDBMS2Graph() {

		int startAt = 0;
		int maxTakenRecordNum = 10;
		List<ORMAuthor> ormAuthors = ormAuthorDAO.fetchWithLimit(startAt, maxTakenRecordNum);

		for (ORMAuthor ormAuthor : ormAuthors) {
			
			System.out.println(ormAuthor.toString());
			
			OGMAuthor ogmAuthor = ormAuthor.toOGMObject();
			ogmAuthorService.save(ogmAuthor);
			
			if(ormAuthor.getAuthorOfPubs()!=null) {
				for(ORMPublication ormAuthorOfPub : ormAuthor.getAuthorOfPubs()) {
					ogmAuthor.getAuthorOfPubs().add(ormAuthorOfPub.toOGMObject());
				}
			}
			
			ogmAuthorService.save(ogmAuthor);
			
		}

	}

	// mapPubFromRDBMS2Graph
	private static void mapPubFromRDBMS2Graph() {

		Session session = HibernateUtil.getSessionFactory().openSession();

		org.neo4j.ogm.session.Session neo4JSession = Neo4jSessionFactory.getInstance().getNeo4jSession();
		Transaction tx = neo4JSession.beginTransaction();

		List<Integer> pubIds = fetchAllPubIdList();

		for (int pubId : pubIds) {

			Query query = session.createQuery("from Publication where id = :id");
			query.setParameter("id", pubId);
			List<ORMPublication> pubs = query.list();

			ORMPublication pubORM = pubs.get(0);

			if (pubORM != null) {

				System.out.println(pubORM.getDblpKey());
				com.dblp2graph.OGM.entity.OGMPublication pubOGM = new com.dblp2graph.OGM.entity.OGMPublication();

				pubOGM = pubORM.toOGMObject();

				if (pubOGM != null) {

					try {

						// add refer to relation
						Set<ORMPublication> referToPubsORM = pubORM.getReferPublishes();
						// System.out.println(referToPubsORM.size());
						if (referToPubsORM != null) {
							for (ORMPublication referToPubORM : referToPubsORM) {
								// System.out.println(referToPubORM.toString());
								pubOGM.getReferToPubs().add(referToPubORM.toOGMObject());
							}
						}

						// add cited by relation
						Set<ORMPublication> citedByPubsORM = pubORM.getCitedPublishes();
						if (citedByPubsORM != null) {
							for (ORMPublication citedByPubORM : citedByPubsORM) {
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

	// fetchAllPubIdList
	private static List<Integer> fetchAllPubIdList() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("select id from Publication");
		List<Integer> pubIds = query.list();
		System.out.println("Fetching total records -> " + pubIds.size());
		return pubIds;
	}

}
