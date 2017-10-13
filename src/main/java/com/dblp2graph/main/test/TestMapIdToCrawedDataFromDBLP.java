package com.dblp2graph.main.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.dblp2graph.ORM.persistence.HibernateUtil;

public class TestMapIdToCrawedDataFromDBLP {

	public static void main(String[] args) {
		
		String rawTextFolderPath = "E:\\Neo4J\\download\\raw_text";
		String desFolderPath = "E:\\Neo4J\\document_content\\crawed_content";
		
		// TODO Auto-generated method stub
		
		List<Object[]> docIds = fetchAllCrawedDoc();
		//System.out.println(docIds.size());
		
		File[] fileList = new File(rawTextFolderPath).listFiles();
		
		for(File file : fileList) {
			
			if(file.isFile()) {
				

				String fileName = file.getName().replace(".txt", ".pdf");
				
				System.out.println(fileName);
				
				for(Object[] docId : docIds) {
					
					if(fileName.equalsIgnoreCase(docId[1].toString())) {
						
						String docIdValue = docId[0].toString();
						//copy & rename file
						String newFileNamePath = desFolderPath + "/" + docIdValue + ".txt";
						try {
							FileUtils.copyFile(file, new File(newFileNamePath));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
				}
				
			}
			
			
		}
		
		
		
		
	}
	
	private static List<Object[]> fetchAllCrawedDoc() {
		
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("select pubId, downloadFileName from PublicationData "
				+ "WHERE downloadFileName IS NOT NULL ORDER BY id ASC");
		List<Object[]> docIds = query.list();
		if(docIds!=null) {
			return docIds;
		}
		
		return null;
		
	}

}
