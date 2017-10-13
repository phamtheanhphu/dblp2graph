package com.dblp2graph.main.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.dblp2graph.ORM.common.entity.ORMAuthor;
import com.dblp2graph.ORM.common.entity.ORMPublication;
import com.dblp2graph.ORM.common.service.dao.ORMPublicationDAO;
import com.dblp2graph.ORM.common.service.dao.impl.ORMPublicationDAOImpl;
import com.dblp2graph.adapter.text.TextFileAdapter;

import javassist.expr.NewArray;

public class TestGeneratePajekNetwork {

	// textFileAdapter
	private static final TextFileAdapter textFileAdapter = new TextFileAdapter();

	// params

	private static final int numberOfLatentTopic = 10;
	private static final int numberofExtractedTermInTopic = 50;
	private static final int numberOfDocLimitation = 500;

	private static final String outputLDADataFilePath = "E:\\Neo4J\\download\\LDA_ouput\\log.txt";
	private static final String outputPajekNetworkDataFilePath = System.getProperty("user.dir")
			+ "/data/output/pajek/TriNhan_HINs.NET";
	private static final String extractedDocumentDataFilePath = System.getProperty("user.dir")
			+ "/data/extracted_document_ids.txt";

	// pajek config
	private static final String topicVertexColor = "Red";
	private static final String termVertexColor = "Blue";
	private static final String docVertexColor = "Green";
	private static final String authorVertexColor = "TealBlue";
	private static final String venueVectexColor = "Orange";

	private static final String arcReferToLineColor = "Red";
	private static final String arcReferToLabelColor = "Blue";

	private static final String arcAuthorOfLineColor = "Red";
	private static final String arcAuthorOfLabelColor = "Blue";

	private static final String arcPublishedAtLineColor = "Red";
	private static final String arcPublishedAtLabelColor = "Blue";

	// RDBMS
	// private static ORMAuthorDAO ormAuthorDAO = new ORMAuthorDAOImpl();
	private static ORMPublicationDAO ormPublicationDAO = new ORMPublicationDAOImpl();

	// main
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// indexDocIdMaps
		HashMap<Integer, Integer> indexDocIdMaps = fetching_IndexDocIdMaps();

		// docIdIndexMaps
		HashMap<Integer, Integer> docIdIndexMaps = fetching_DocIdIndexMaps();

		// extractingPossibleDocument
		// extractingPossibleDocument(docIdIndexMaps);
		generatePajekNetworkFile(indexDocIdMaps);

		// ORMPublication ormPublication =
		// ormPublicationDAO.findPubById(515554);
		// System.out.println(ormPublication.getSourceId());
	}

	// extractingPossibleDocument
	private static void extractingPossibleDocument(HashMap<Integer, Integer> docIdIndexMaps) {

		List<String> extractedDocumentIds = new ArrayList<>();

		List<ORMPublication> possibleTakenDocuments = new ArrayList<>();

		for (Entry<Integer, Integer> docIdIndexMap : docIdIndexMaps.entrySet()) {

			// System.out.println(docIdIndexMap.getKey());

			if (possibleTakenDocuments.size() >= numberOfDocLimitation) {

				break;
			}

			ORMPublication ormPub = ormPublicationDAO.findPubById(docIdIndexMap.getKey());

			if (ormPub != null) {

				Set<ORMPublication> referToDocs = ormPub.getReferPublishes();

				if (referToDocs != null && referToDocs.size() > 0) {

					int totalReferExistedDocument = 0;

					for (ORMPublication referToPub : referToDocs) {

						if (docIdIndexMaps.get(referToPub.getId()) != null) {
							totalReferExistedDocument++;
						}

					}

					if (totalReferExistedDocument > 0) {

						if (!possibleTakenDocuments.contains(ormPub)) {
							possibleTakenDocuments.add(ormPub);
							extractedDocumentIds.add(String.valueOf(ormPub.getId()));
						}

						if (!possibleTakenDocuments.contains(referToDocs)) {
							possibleTakenDocuments.addAll(referToDocs);
							for (ORMPublication referToDoc : referToDocs) {
								if (!extractedDocumentIds.contains(String.valueOf(referToDoc.getId()))) {
									extractedDocumentIds.add(String.valueOf(referToDoc.getId()));
								}

							}
						}

					} else {
						continue;
					}

				} else {
					continue;
				}

			}

		}

		System.out.println("Total possible taken document -> [" + extractedDocumentIds.size() + "] !");

		textFileAdapter.writeToDataFile(extractedDocumentIds, extractedDocumentDataFilePath);

	}

	private static void generatePajekNetworkFile(HashMap<Integer, Integer> indexDocIdMaps) {

		System.out.println("Fetching index document's id maps -> [" + indexDocIdMaps.size() + "] records !");

		// extractedTopics
		List<LDA_TopicTermDistribution> extractedTopics = fetchingLDA_TopicTermDistribution();

		// extractedDocDistributions
		List<LDA_DocTopicDistribution> extractedDocDistributions = fetchingLDA_DocTopicDistribution();

		// building the Pajek Network file
		int numberOfVertices = numberOfLatentTopic + (numberOfLatentTopic * numberofExtractedTermInTopic)
				+ numberOfDocLimitation;

		HashMap<Integer, String> addedTerms = new HashMap<>();

		String verticesString = "";
		String arcsString = "";
		textFileAdapter.clearTheFileContent(outputPajekNetworkDataFilePath);

		int[] topicVertexIds = new int[numberOfLatentTopic];

		// mapping vertices
		int vertexCount = 1;

		int currentTopicVertexId = 1;

		for (LDA_TopicTermDistribution extractedTopic : extractedTopics) {

			verticesString += "\t" + vertexCount + " " + "\"topic_" + extractedTopic.id + "\" ic " + topicVertexColor
					+ "\n";

			currentTopicVertexId = vertexCount;

			topicVertexIds[extractedTopic.id] = currentTopicVertexId;

			vertexCount++;

			/*
			 * for (Entry<String, Double> termPair :
			 * extractedTopic.termPairs.entrySet()) {
			 * 
			 * int currentTermVertexId = vertexCount;
			 * 
			 * if (!addedTerms.values().contains(termPair.getKey())) {
			 * 
			 * verticesString += "\t" + vertexCount + " \"" + termPair.getKey()
			 * + "\" ic " + termVertexColor + "\n"; vertexCount++;
			 * addedTerms.put(vertexCount, termPair.getKey());
			 * 
			 * } else { for (Entry<Integer, String> entry :
			 * addedTerms.entrySet()) { if
			 * (entry.getValue().equalsIgnoreCase(termPair.getKey())) {
			 * currentTermVertexId = entry.getKey(); } } }
			 * 
			 * arcsString += "\t" + currentTopicVertexId + " " +
			 * currentTermVertexId + " " + termPair.getValue() + "\n";
			 * 
			 * }
			 */

		}

		// mapping the document
		List<String> extractedDocumentIds = textFileAdapter.parseSingleFileToListString(extractedDocumentDataFilePath);
		List<ORMPublication> addORMPubs = new ArrayList<>();

		int docCount = 0;
		HashMap<Integer, Integer> docIdVerTextIdMaps = new HashMap<>();
		HashMap<String, Integer> venueVerTextIdMaps = new HashMap<>();

		for (String extractedDocumentId : extractedDocumentIds) {

			for (LDA_DocTopicDistribution extractedDocDistribution : extractedDocDistributions) {

				int dblpDocId = indexDocIdMaps.get(extractedDocDistribution.id);

				if (dblpDocId == Integer.parseInt(extractedDocumentId)) {

					ORMPublication ormPub = ormPublicationDAO.findPubById(dblpDocId);

					if (ormPub != null) {
						addORMPubs.add(ormPub);

					}

					verticesString += "\t" + vertexCount + " \"" + "doc_" + dblpDocId + "\" ic " + docVertexColor
							+ "\n";

					for (Entry<Integer, Double> entry : extractedDocDistribution.docTopicDistributions.entrySet()) {
						arcsString += "\t" + vertexCount + " " + topicVertexIds[entry.getKey()] + " " + entry.getValue()
								+ "\n";
					}

					docIdVerTextIdMaps.put(dblpDocId, vertexCount);

					docCount++;
					vertexCount++;

				} else {
					continue;
				}

			}

		}

		// mapping author & citation relation
		for (ORMPublication addORMPub : addORMPubs) {

			Set<ORMPublication> referredORMPubs = addORMPub.getReferPublishes();
			Set<ORMAuthor> ormAuthors = addORMPub.getAuthors();

			// venue
			if (venueVerTextIdMaps.get(addORMPub.getSourceId()) == null) {
				
				
				verticesString += "\t" + vertexCount + " \"" + addORMPub.getSourceId() + "\" ic " + venueVectexColor
						+ "\n";
				arcsString += "\t" + docIdVerTextIdMaps.get(addORMPub.getId()) + " "
						+ vertexCount + " 1 c " + arcPublishedAtLineColor
						+ " l \"published_at: "+addORMPub.getYear()+"\"  lc " + arcPublishedAtLineColor + " \n";
				venueVerTextIdMaps.put(addORMPub.getSourceId(), vertexCount);
				vertexCount++;
				
			}

			// citation relation
			for (ORMPublication referredORMPub : referredORMPubs) {

				if (addORMPubs.contains(referredORMPub)) {

					// System.out.println("Document [" + addORMPub.getId() + "]
					// -> [" + referredORMPub.getId() + "]");

					arcsString += "\t" + docIdVerTextIdMaps.get(addORMPub.getId()) + " "
							+ docIdVerTextIdMaps.get(referredORMPub.getId()) + " 1 c " + arcReferToLineColor
							+ " l refer_to lc " + arcReferToLabelColor + " \n";
				}

			}

			// authors
			if (ormAuthors != null && ormAuthors.size() > 0) {

				for (ORMAuthor ormAuthor : ormAuthors) {

					verticesString += "\t" + vertexCount + " \"" + ormAuthor.getAuthor() + "\" ic " + authorVertexColor
							+ "\n";
					arcsString += "\t" + vertexCount + " " + docIdVerTextIdMaps.get(addORMPub.getId()) + " 1 c "
							+ arcAuthorOfLineColor + " l author_of lc " + arcAuthorOfLabelColor + " \n";
					vertexCount++;

				}

			}

		}

		// mapping vertices & arcs
		textFileAdapter.writeAppendToFile(
				"*Vertices\t" + (vertexCount - 1) + "\n" + verticesString + "*Arcs" + "\n" + arcsString,
				outputPajekNetworkDataFilePath);
	}

	// fetching_DocIdIndexMaps
	private static HashMap<Integer, Integer> fetching_DocIdIndexMaps() {

		HashMap<Integer, Integer> indexDocIdMaps = new HashMap<>();

		int lineCount = 0;

		boolean isStartDataBlock = false;
		String patternRecognition = "[Index-DocName]";

		try (BufferedReader br = Files.newBufferedReader(Paths.get(outputLDADataFilePath), StandardCharsets.UTF_8)) {

			for (String line = null; (line = br.readLine()) != null;) {

				lineCount++;

				if (line.equalsIgnoreCase(patternRecognition)) {
					isStartDataBlock = true;
					continue;
				}

				if (isStartDataBlock && !line.equalsIgnoreCase("") && !line.equalsIgnoreCase("[Phi]")) {

					try {

						// System.out.println(line);
						String[] splits = line.split("\t");
						indexDocIdMaps.put(Integer.parseInt(splits[1].replace(".txt", "")),
								Integer.parseInt(splits[0]));

					} catch (ArrayIndexOutOfBoundsException ex) {
						// System.out.println(line);
						break;
					}

				} else {
					break;
				}

			}

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return indexDocIdMaps;

	}

	// fetching_IndexDocIdMaps
	private static HashMap<Integer, Integer> fetching_IndexDocIdMaps() {

		HashMap<Integer, Integer> indexDocIdMaps = new HashMap<>();

		int lineCount = 0;

		boolean isStartDataBlock = false;
		String patternRecognition = "[Index-DocName]";

		try (BufferedReader br = Files.newBufferedReader(Paths.get(outputLDADataFilePath), StandardCharsets.UTF_8)) {

			for (String line = null; (line = br.readLine()) != null;) {

				lineCount++;

				if (line.equalsIgnoreCase(patternRecognition)) {
					isStartDataBlock = true;
					continue;
				}

				if (isStartDataBlock && !line.equalsIgnoreCase("") && !line.equalsIgnoreCase("[Phi]")) {

					try {

						// System.out.println(line);
						String[] splits = line.split("\t");
						indexDocIdMaps.put(Integer.parseInt(splits[0]),
								Integer.parseInt(splits[1].replace(".txt", "")));

					} catch (ArrayIndexOutOfBoundsException ex) {
						// System.out.println(line);
						break;
					}

				} else {
					break;
				}

			}

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return indexDocIdMaps;

	}

	// fetchingLDA_DocTopicDistribution
	private static List<LDA_DocTopicDistribution> fetchingLDA_DocTopicDistribution() {

		List<LDA_DocTopicDistribution> extractedList = new ArrayList<>();

		String patternRecognition = "[Theta]";
		int lineCount = 0;

		boolean isStartDataBlock = false;
		boolean isStartNewTopicDataBlock = false;

		LDA_DocTopicDistribution ldaDocTopicDistribution = null;

		try (BufferedReader br = Files.newBufferedReader(Paths.get(outputLDADataFilePath), StandardCharsets.UTF_8)) {

			for (String line = null; (line = br.readLine()) != null;) {

				lineCount++;
				if (line.equalsIgnoreCase(patternRecognition)) {
					isStartDataBlock = true;
					continue;
				}

				if (isStartDataBlock && !line.equalsIgnoreCase("")) {

					try {

						if (line.startsWith("d#")) {

							// System.out.println(line);

							// mapping to array
							if (ldaDocTopicDistribution != null) {
								extractedList.add(ldaDocTopicDistribution);
							}

							String[] splits = line.split("\t");

							ldaDocTopicDistribution = new LDA_DocTopicDistribution();
							ldaDocTopicDistribution.id = Integer.parseInt(splits[0].replace("d#", ""));

							for (int sliding = 1; sliding <= numberOfLatentTopic; sliding++) {
								ldaDocTopicDistribution.docTopicDistributions.put((sliding - 1),
										Double.parseDouble(splits[sliding]));
							}

						}

					} catch (ArrayIndexOutOfBoundsException ex) {
						// System.out.println(line);
						continue;
					}

				}

			}

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		return extractedList;

	}

	// fetchingLDA_TopicTermDistribution
	private static List<LDA_TopicTermDistribution> fetchingLDA_TopicTermDistribution() {

		List<LDA_TopicTermDistribution> extractedList = new ArrayList<>();

		String patternRecognition = "[Phi]";
		int lineCount = 0;

		boolean isStartDataBlock = false;
		boolean isStartNewTopicDataBlock = false;

		LDA_TopicTermDistribution ldaTopicTermDistribution = null;

		try (BufferedReader br = Files.newBufferedReader(Paths.get(outputLDADataFilePath), StandardCharsets.UTF_8)) {

			for (String line = null; (line = br.readLine()) != null;) {

				lineCount++;
				if (line.equalsIgnoreCase("[Phi]")) {
					isStartDataBlock = true;
					continue;
				}

				if (isStartDataBlock && !line.equalsIgnoreCase("[Theta]")) {

					try {

						if (line.startsWith("t#")) {

							// System.out.println(line);

							// mapping to array
							if (ldaTopicTermDistribution != null) {
								extractedList.add(ldaTopicTermDistribution);
							}

							ldaTopicTermDistribution = new LDA_TopicTermDistribution();
							ldaTopicTermDistribution.id = Integer.parseInt(line.replace("t#", ""));
							isStartNewTopicDataBlock = true;

							continue;
						}

						if (isStartNewTopicDataBlock) {
							String[] splits = line.split("=");
							ldaTopicTermDistribution.termPairs.put(splits[0], Double.parseDouble(splits[1]));
							continue;
						}

					} catch (ArrayIndexOutOfBoundsException ex) {
						// System.out.println(line);
						continue;
					}

				}

				if (line.equalsIgnoreCase("[Theta]")) {
					extractedList.add(ldaTopicTermDistribution);
					break;
				}

			}

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();

		}

		return extractedList;

	}

	// LDA_TopicTermDistribution
	private static class LDA_TopicTermDistribution {
		public int id;
		public HashMap<String, Double> termPairs = new HashMap<>();
	}

	private static class LDA_DocTopicDistribution {
		public int id;
		public HashMap<Integer, Double> docTopicDistributions = new HashMap<>();
	}

}
