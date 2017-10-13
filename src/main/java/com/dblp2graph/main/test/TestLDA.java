package com.dblp2graph.main.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dblp2graph.adapter.text.TextFileAdapter;
import com.dblp2graph.algorithm.LDA.handler.Corpus;
import com.dblp2graph.algorithm.LDA.handler.LdaGibbsSampler;
import com.dblp2graph.algorithm.LDA.handler.LdaUtil;

public class TestLDA {

	private static TextFileAdapter textFileAdapter = new TextFileAdapter();

	public static void main(String[] args) throws IOException {

		// TODO Auto-generated method stub
		String ldaLogFilePath = "E:\\Neo4J\\download\\LDA_ouput\\log.txt";

		textFileAdapter.clearTheFileContent(ldaLogFilePath);

		int numberOfTopic = 10;
		int numberOfTakenOutWord = 50;
		String folderPath = "E:\\Neo4J\\document_content\\preprocessed_text";

		// 1. Load corpus from disk
		Corpus corpus = Corpus.load(folderPath);

		// 2. Create a LDA sampler
		LdaGibbsSampler ldaGibbsSampler = new LdaGibbsSampler(corpus.getDocument(), corpus.getVocabularySize());

		// 3. Train it
		ldaGibbsSampler.gibbs(numberOfTopic);

		// 4. The phi matrix is a LDA model, you can use LdaUtil to
		// explain it.
		double[][] phi = ldaGibbsSampler.getPhi();

		
		//document index & name mapping
		textFileAdapter.writeAppendToFile("[Index-DocName]", ldaLogFilePath);
		Map<Integer, String> indexDocumentMaps = corpus.getIndexDocumentMaps();
		
		if (indexDocumentMaps != null) {
			for (Entry<Integer, String> entry : indexDocumentMaps.entrySet()) {
				textFileAdapter.writeAppendToFile(entry.getKey() + "\t" + entry.getValue(), ldaLogFilePath);
			}
		}
		
		try {

			Map<String, Double>[] topicMaps = LdaUtil.translate(phi, corpus.getVocabulary(), numberOfTakenOutWord);
			
			// phi calculation
			LdaUtil.explain(topicMaps);
			LdaUtil.exportPhiToLogFile(topicMaps, ldaLogFilePath);

			// theta calculation
			double[][] theta = ldaGibbsSampler.getTheta();
			LdaUtil.dispThetaInNum(theta);
			LdaUtil.dispThetaInNumToLogFile(theta, ldaLogFilePath);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
