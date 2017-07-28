package com.dblp2graph.dblp2graph;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dblp2graph.common.Publish;
import com.dblp2graph.common.publish.PublishData;
import com.dblp2graph.persistence.HibernateUtil;
import com.dblp2graph.tool.crawler.AbstractWebCrawler;

public class AppCrawler {

	private static final String outputFolderPath = System.getProperty("user.dir") + "/data/download";

	public static void main(String[] args) {

		if (args.length > 0) {

			int startRange = Integer.parseInt(args[0].replaceAll("--startRange=", ""));
			int endRange = Integer.parseInt(args[1].replaceAll("--endRange=", ""));

			System.out.println("Starting range = [" + startRange + "] and ending range = [" + endRange + "]");

			// TODO Auto-generated method stub
			Session session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery("select id from Publish where id NOT IN (select pubId from PublishData) "
					+ "AND publishDOIPdfUrl is null ORDER BY id ASC");
			query.setFirstResult(startRange);
			query.setMaxResults(endRange - startRange);

			List<Integer> publisheIds = query.list();

			System.out.println("Total extracting publish -> [" + publisheIds.size() + "] records !");

			for (Integer id : publisheIds) {

				query = session.createQuery("from Publish where id = :id");

				query.setParameter("id", id);
				List<Publish> publishes = query.list();
				if (publishes != null) {
					Publish publish = publishes.get(0);
					if (publish != null) {
						if (publish.getPublishDOIUrl() != null) {
							PublishData publishRaw = AbstractWebCrawler.proceed(publish.getPublishDOIUrl(),
									outputFolderPath);
							if (publishRaw != null) {
								publishRaw.setPubId(id);
								session.beginTransaction();
								session.save(publishRaw);
								session.getTransaction().commit();
							}
						}
					}
				}

			}

		} else {
			System.out.println("Invalid arguments !");
		}

		/*
		 * String outputFolderPath = System.getProperty("user.dir") +
		 * "/data/download"; String dblpDOIUrl =
		 * "http://dx.doi.org/10.1007/BF01177548";
		 * AbstractWebCrawler.proceed(dblpDOIUrl, outputFolderPath,
		 * "BF01177548.pdf");
		 */
	}

}
