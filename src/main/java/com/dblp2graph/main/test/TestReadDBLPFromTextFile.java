package com.dblp2graph.main.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dblp2graph.ORM.common.entity.ORMPublication;
import com.dblp2graph.ORM.persistence.HibernateUtil;
import com.dblp2graph.adapter.text.TextFileAdapter;

public class TestReadDBLPFromTextFile {

	private static TextFileAdapter textFileAdapter = new TextFileAdapter();

	private static class DBLPIdTitleMap {

		public String id;
		public String title;

		public DBLPIdTitleMap(String id, String title) {
			this.id = id;
			this.title = title;
		}
	}

	private static class AminerTitleAbstractMap {

		public String id;
		public String title;
		public String abstractContent;

		public AminerTitleAbstractMap(String id, String title, String abstractContent) {
			this.id = id;
			this.title = title;
			this.abstractContent = abstractContent;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return this.id + "\t" + this.title + "\t" + this.abstractContent;
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// fetchingMappingPairs();

		String dataFilePath = "E:\\Neo4J\\DBLPOnlyCitationOct19.txt";

		String dblpIdTitleMaps1FilePath = "E:\\Neo4J\\dblp_id_title_maps_0_2000000.txt";
		String dblpIdTitleMaps2FilePath = "E:\\Neo4J\\dblp_id_title_maps_2000001_end.txt";

		String aminerTitleAbstractMapsFilePath = "E:\\Neo4J\\aminer_title_abstract_maps.txt";
		String aminerIdTitleAbstractMapsFilePath = "E:\\Neo4J\\aminer_id_title_abstract_maps.txt";

		List<DBLPIdTitleMap> dblpIdTitleMaps = new ArrayList<>();
		List<String> titleAbstractMaps = new ArrayList<>();
		List<AminerTitleAbstractMap> idTitleAbstractMaps = new ArrayList<>();
		List<String> alreadyAddIdTitleAbstractTitleList= new ArrayList<>();
		
		try (BufferedReader br = Files.newBufferedReader(Paths.get(aminerIdTitleAbstractMapsFilePath),
				StandardCharsets.UTF_8)) {
			
			for (String line = null; (line = br.readLine()) != null;) {
				
				String[] splits = line.split("\t");
				String id = splits[0];
				String title = splits[1];
				if(title!=null) {
					alreadyAddIdTitleAbstractTitleList.add(title);
				}

			}
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Total fetching already added dblpIdTitleMaps -> [" + alreadyAddIdTitleAbstractTitleList.size() + "]");
		
		int lineCount = 1;
		
		try (BufferedReader br = Files.newBufferedReader(Paths.get(dblpIdTitleMaps1FilePath), StandardCharsets.UTF_8)) {
			for (String line = null; (line = br.readLine()) != null;) {
				String[] splits = line.split("\t");
				try {
					if (splits[0] != null && splits[1] != null) {
						dblpIdTitleMaps.add(new DBLPIdTitleMap(splits[0], splits[1]));
					} else {
						// System.out.println("Error at line -> [" + lineCount
						// +"]");
					}
					lineCount++;
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.out.println("Error at line -> [" + lineCount + "]");
				}

			}
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();

		}

		try (BufferedReader br = Files.newBufferedReader(Paths.get(dblpIdTitleMaps2FilePath), StandardCharsets.UTF_8)) {
			for (String line = null; (line = br.readLine()) != null;) {
				String[] splits = line.split("\t");
				try {
					if (splits[0] != null && splits[1] != null) {
						dblpIdTitleMaps.add(new DBLPIdTitleMap(splits[0], splits[1]));
					} else {
						// System.out.println("Error at line -> [" + lineCount
						// +"]");
					}
					lineCount++;
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.out.println("Error at line -> [" + lineCount + "]");
				}

			}
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Total fetching dblpIdTitleMaps -> [" + dblpIdTitleMaps.size() + "]");
		
		try (BufferedReader br = Files.newBufferedReader(Paths.get(aminerTitleAbstractMapsFilePath),
				StandardCharsets.UTF_8)) {
			
			for (String line = null; (line = br.readLine()) != null;) {
				
				String[] splits = line.split("\t");
				String title = splits[0];
				String abstractContent = splits[1];
				
				if(!alreadyAddIdTitleAbstractTitleList.contains(title)) {
					
					for (DBLPIdTitleMap dblpIdTitleMap : dblpIdTitleMaps) {

						if (dblpIdTitleMap.title.equalsIgnoreCase(title)) {
							AminerTitleAbstractMap aminerTitleAbstractMap = new AminerTitleAbstractMap(dblpIdTitleMap.id,
									title, abstractContent);
							idTitleAbstractMaps.add(aminerTitleAbstractMap);
							System.out.println("Mapping document's id -> [" + dblpIdTitleMap.id + "]");
							textFileAdapter.writeAppendToFile(aminerTitleAbstractMap.toString(),
									aminerIdTitleAbstractMapsFilePath);
							break;
						}

					}
					
				} else {
					System.out.println("Skipping document -> [" + title + "]");
				}

			}
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Total fetching idTitleAbstractMaps -> [" + idTitleAbstractMaps.size() + "]");

		/*
		 * try (BufferedReader br =
		 * Files.newBufferedReader(Paths.get(dataFilePath),
		 * StandardCharsets.UTF_8)) {
		 * 
		 * boolean isNewDocument = false; boolean slidedToTitle = false; String
		 * docTitle = ""; String abstractContent = "";
		 * 
		 * for (String line = null; (line = br.readLine()) != null;) {
		 * 
		 * if (isNewDocument) { docTitle = ""; abstractContent = "";
		 * isNewDocument = false; } else {
		 * 
		 * if (line.startsWith("#*")) {
		 * 
		 * if (slidedToTitle) { isNewDocument = true; } else { docTitle =
		 * line.replace("#*", "").trim(); slidedToTitle = true; }
		 * 
		 * }
		 * 
		 * if (line.startsWith("#!")) { if (slidedToTitle) { abstractContent =
		 * line.replace("#!", "").trim(); titleAbstractMaps.add(docTitle + "\t"
		 * + abstractContent); // System.out.println(docTitle + " - " + //
		 * abstractContent); slidedToTitle = false; isNewDocument = true; }
		 * 
		 * }
		 * 
		 * }
		 * 
		 * } } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 * 
		 * System.out.println("Total fetching document -> [" +
		 * titleAbstractMaps.size() + "]");
		 * 
		 * textFileAdapter.writeToDataFile(titleAbstractMaps,
		 * aminerTitleAbstractMapsFilePath);
		 */

		/*
		 * List<Object[]> pubORMs = fetchingMappingPairs();
		 * 
		 * System.out.println("Total fetching document from DBLP -> [" +
		 * pubORMs.size() + "]"); List<String> outputs = new ArrayList<>(); for
		 * (Object[] pubORM : pubORMs) { outputs.add(pubORM[0] + "\t" +
		 * pubORM[1]); // System.out.println("Fetching total records -> [" +
		 * pubORM[0] + " // " + pubORM[1] + "]");
		 * 
		 * }
		 * 
		 * textFileAdapter.writeToDataFile(outputs, dblpIdTitleMapsFilePath);
		 */

	}

	private static Object[] findDocumentByTitle(String title) {
		int startAt = 0;
		int maxNum = 1000;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("select id, title from Publication WHERE title = :title ORDER BY id ASC");
		query.setParameter("title", title);
		query.setFirstResult(startAt);
		query.setMaxResults(maxNum);
		List<Object[]> pubORMs = query.list();

		if (pubORMs != null) {
			return pubORMs.get(0);
		}

		return null;

		/*
		 * for (Object[] pubORM : pubORMs) { System.out.println(
		 * "Fetching total records -> [" + pubORM[0] + " " + pubORM[1] + "]"); }
		 */
	}

	private static List<Object[]> fetchingMappingPairs() {

		int startAt = 2000001;
		int maxNum = 2000000;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("select id, title from Publication ORDER BY id ASC");
		query.setFirstResult(startAt);
		query.setMaxResults(maxNum);
		List<Object[]> pubORMs = query.list();

		return pubORMs;

	}

}
