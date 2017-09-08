package com.dblp2graph.main;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.neo4j.ogm.transaction.Transaction;

import com.dblp2graph.OGM.entity.OGMPublication;
import com.dblp2graph.OGM.factory.Neo4jSessionFactory;
import com.dblp2graph.OGM.service.OGMAuthorService;
import com.dblp2graph.OGM.service.OGMPublicationService;
import com.dblp2graph.OGM.service.impl.OGMAuthorServiceImpl;
import com.dblp2graph.OGM.service.impl.OGMPublicationServiceImpl;
import com.dblp2graph.ORM.common.entity.ORMPublication;
import com.dblp2graph.ORM.common.service.dao.ORMAuthorDAO;
import com.dblp2graph.ORM.common.service.dao.ORMPublicationDAO;
import com.dblp2graph.ORM.common.service.dao.impl.ORMAuthorDAOImpl;
import com.dblp2graph.ORM.common.service.dao.impl.ORMPublicationDAOImpl;
import com.dblp2graph.ORM.persistence.HibernateUtil;

public class AppPubORM2OGMMapper {

	// RDBMS
	private static ORMAuthorDAO ormAuthorDAO = new ORMAuthorDAOImpl();
	private static ORMPublicationDAO ormPublicationDAO = new ORMPublicationDAOImpl();

	// Graph
	private static OGMPublicationService ogmPublicationService = new OGMPublicationServiceImpl();
	private static OGMAuthorService ogmAuthorService = new OGMAuthorServiceImpl();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length > 0) {
			int startAt = Integer.parseInt(args[0].replaceAll("--startAt=", "").trim());
			int maxNum = Integer.parseInt(args[1].replaceAll("--maxNum=", "").trim());
			mapPubFromRDBMS2Graph(startAt, maxNum);
		} else {
			System.out.println("Invalid arguments !");
		}

	}

	// mapPubFromRDBMS2Graph
	private static void mapPubFromRDBMS2Graph(int startAt, int maxNum) {

		List<Integer> pubIds = fetchAllPubIdList(startAt, maxNum);

		for (int pubId : pubIds) {

			ORMPublication pubORM = ormPublicationDAO.findPubById(pubId);

			if (pubORM != null) {

				
				OGMPublication pubOGM = new OGMPublication();
				pubOGM = pubORM.toOGMObject();

				if (pubOGM != null) {

					try {
						
						System.out.println(pubORM.getDblpKey());
						
						// add refer to relation
						Set<ORMPublication> referToPubsORM = pubORM.getReferPublishes();
						if (referToPubsORM != null) {
							for (ORMPublication referToPubORM : referToPubsORM) {
								// System.out.println(referToPubORM.toString());
								pubOGM.getReferToPubs().add(referToPubORM.toOGMObject());
							}
						}

						// add cited by relation
						/*Set<ORMPublication> citedByPubsORM = pubORM.getCitedPublishes();
						if (citedByPubsORM != null) {
							for (ORMPublication citedByPubORM : citedByPubsORM) {
								pubOGM.getCitedPubs().add(citedByPubORM.toOGMObject());
							}
						}*/

						ogmPublicationService.save(pubOGM);

					} catch (Exception e) {
						System.out.println(e.getMessage());
						continue;
					}
				}
			}
		}

	}
	
	
	// fetchAllPubIdList
	private static List<Integer> fetchAllPubIdList(int startAt, int maxNum) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("select id from Publication ORDER BY id ASC");
		query.setFirstResult(startAt);
		query.setMaxResults(maxNum);
		List<Integer> pubIds = query.list();
		System.out.println("Fetching total records -> [" + pubIds.size() + "]");
		return pubIds;
	}

}
