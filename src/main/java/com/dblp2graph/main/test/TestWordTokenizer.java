package com.dblp2graph.main.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.dblp2graph.adapter.text.TextFileAdapter;
import com.dblp2graph.tool.crawler.text_analyzer.WordTokenizer;

import scala.reflect.internal.Trees.This;

public class TestWordTokenizer {

	private static TextFileAdapter textFileAdapter = new TextFileAdapter();
	private static WordTokenizer wordTokenizer = new WordTokenizer(false, true, true, true, true, null, 3);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String rawTextFileFolderPath = "E:\\Neo4J\\download\\raw_text";
		String stemmedTextFileFolderPath = "E:\\Neo4J\\download\\stemmed_text";

		//cleaning all current files
		try {
			FileUtils.cleanDirectory(new File(stemmedTextFileFolderPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int fileCount = 0;
		int fileNumberLimitation = 10;

		File[] rawTextFiles = new File(rawTextFileFolderPath).listFiles();
		for (File rawTextFile : rawTextFiles) {

			if (rawTextFile.isFile()) {

				if (fileCount < fileNumberLimitation) {
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
							stemmedTextFileFolderPath + "/" + rawTextFile.getName());
					// System.out.println(stringBuilder.toString().trim());

					fileCount++;
				}

			}

		}

		// wordTokenizer.proceedText();

	}

}
