package com.dblp2graph.main.test;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dblp2graph.adapter.text.TextFileAdapter;
import com.dblp2graph.tool.crawler.text_analyzer.WordTokenizer;

public class TestMergeAndPreprocessText {
	
	private static TextFileAdapter textFileAdapter = new TextFileAdapter();
	private static WordTokenizer wordTokenizer = new WordTokenizer(true, true, true, true, true, null, 2);
	
	public static void main(String[] args) {

		// TODO Auto-generated method stub
		String abstractFolderPath = "E:\\Neo4J\\document_content\\aminer_abstracts";
		String crawedContentFolderPath = "E:\\Neo4J\\document_content\\crawed_content";
		String desFolder = "E:\\Neo4J\\document_content\\preprocessed_text";
		
		//abstractFolderPath
		preprocessAndCopyFileToDesFolder(abstractFolderPath, desFolder);
		
		//crawedContentFolderPath
		preprocessAndCopyFileToDesFolder(crawedContentFolderPath, desFolder);

	}

	private static void preprocessAndCopyFileToDesFolder(String sourceFolder, String desFolder) {

		File[] rawTextFiles = new File(sourceFolder).listFiles();

		for (File rawTextFile : rawTextFiles) {

			if (rawTextFile.isFile()) {

				List<String> tokens = wordTokenizer.proceedFile(rawTextFile.getAbsolutePath());
				StringBuilder stringBuilder = new StringBuilder();

				for (String token : tokens) {

					if (!StringUtils.isNumeric(token)) {

						if (token != "" && token.trim().length() > 0) {

							stringBuilder.append(token).append(" ");

						}
					}

				}

				textFileAdapter.writeToDataStringFile(stringBuilder.toString().trim(),
						desFolder + "/" + rawTextFile.getName());

			}

		}

	}

}
