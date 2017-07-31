package com.dblp2graph.dblp2graph;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dblp2graph.common.publication.PublicationData;
import com.dblp2graph.persistence.HibernateUtil;
import com.dblp2graph.tool.crawler.AbstractWebCrawler;

public class AppPublishDataUpdater {

	private static final String outputFolderPath = System.getProperty("user.dir") + "/data/download";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Query query = session
				.createQuery("from PublishData " + "where dom_content is null AND (accessUrl NOT LIKE :accessUrl1 "
						+ "AND accessUrl  NOT LIKE :accessUrl2 AND accessUrl  NOT LIKE :accessUrl3)");
		query.setParameter("accessUrl1", "https://link.springer.com%");
		query.setParameter("accessUrl2", "http://ieeexplore.ieee.org:80/%");
		query.setParameter("accessUrl3", "http://www.inderscience.com/");
		
		/*Query query = session
				.createQuery("from PublishData " + "where dom_content is null AND accessUrl LIKE :accessUrl");
		query.setParameter("accessUrl", "https://www.igi-global.com%");*/

		List<PublicationData> pubDataList = query.list();

		System.out.println("Extracting total -> [" + pubDataList.size() + "] for updating !");

		for (PublicationData publishData : pubDataList) {
			try {
				if (publishData.getAccessUrl() != null) {
					
					PublicationData publishRaw = AbstractWebCrawler.proceed(publishData.getAccessUrl(), outputFolderPath);
					
					if (publishRaw.getDomDontent() != null) {
						
						session.beginTransaction();
						publishData.setDomDontent(publishRaw.getDomDontent());
						session.update(publishData);
						session.getTransaction().commit();
					}

				}
			} catch (Exception e) {
				continue;
			}

		}

	}

}
