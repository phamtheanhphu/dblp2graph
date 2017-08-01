package com.dblp2graph.main;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dblp2graph.OGM.entity.OGMAuthor;
import com.dblp2graph.OGM.entity.OGMPublication;
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

public class AppAuthorORM2OGMMapper {

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
			mapAuthorFromRDBMS2Graph(startAt, maxNum);
		} else {
			System.out.println("Invalid arguments !");
		}
	}

	// mapAuthorFromRDBMS2Graph
	private static void mapAuthorFromRDBMS2Graph(int startAt, int maxNum) {

		List<Integer> authorIds = fetchAllAuthorIdList(startAt, maxNum);

		for (int authorId : authorIds) {

			ORMAuthor ormAuthor = ormAuthorDAO.findAuthorById(authorId);

			if (ormAuthor != null) {

				OGMAuthor ogmAuthor = ormAuthor.toOGMObject();

				if (ogmAuthor != null) {

					try {

						System.out.println(ormAuthor.getAuthor());

						// written publications
						Set<ORMPublication> authorOfPubs = ormAuthor.getAuthorOfPubs();
						if (authorOfPubs != null) {
							for (ORMPublication authorOfPub : authorOfPubs) {
								ogmAuthor.getAuthorOfPubs().add(authorOfPub.toOGMObject());
							}
						}

						ogmAuthorService.save(ogmAuthor);

					} catch (Exception e) {
						System.out.println(e.getMessage());
						continue;
					}
				}
			}
		}

	}

	private static List<Integer> fetchAllAuthorIdList(int startAt, int maxNum) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("select id from Author ORDER BY id ASC");
		query.setFirstResult(startAt);
		query.setMaxResults(maxNum);
		List<Integer> pubIds = query.list();
		System.out.println("Fetching total records -> [" + pubIds.size() + "]");
		return pubIds;
	}

}
