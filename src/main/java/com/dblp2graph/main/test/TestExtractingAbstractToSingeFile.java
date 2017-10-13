package com.dblp2graph.main.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.dblp2graph.adapter.text.TextFileAdapter;
import com.google.protobuf.compiler.PluginProtos.CodeGeneratorResponse.File;

public class TestExtractingAbstractToSingeFile {

	private static TextFileAdapter textFileAdapter = new TextFileAdapter();

	public static void main(String[] args)  {

		// TODO Auto-generated method stub

		String aminerIdTitleAbstractMapsFilePath = "E:\\Neo4J\\aminer_id_title_abstract_maps.txt";

		/*List<String> alreadyAddedFileList = new ArrayList<>();
		String outputDirPath = "E:\\Neo4J\\document_content\\aminer_abstracts";

		int countFile = 1;

		java.io.File[] adreadyAddedFiles = new java.io.File(outputDirPath).listFiles();
		for (java.io.File file : adreadyAddedFiles) {
			if (file.isFile() && file.getName().endsWith(".txt")) {
				alreadyAddedFileList.add(file.getName().replace(".txt", ""));
			}
		}

		System.out.println(alreadyAddedFileList.size());*/

		int lineCount = 0;
		
		try (BufferedReader br = Files.newBufferedReader(Paths.get(aminerIdTitleAbstractMapsFilePath),
				StandardCharsets.ISO_8859_1)) {
			
			for (String line = null; (line = br.readLine()) != null;) {

				/*String[] splits = line.split("\t");

				try {

					String id = splits[0];
					String title = splits[1];
					String abstractContent = splits[2];
					
					if(!alreadyAddedFileList.contains(id)) {
						String outputFilePath = outputDirPath + "/" + id + ".txt";
						textFileAdapter.writeToDataStringFile(title + "\n" + abstractContent, outputFilePath);
					}

				
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.out.println("Error at -> [" + countFile + "]");
					continue;
				}

				countFile++;*/
				lineCount++;
				

			}

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		System.out.println(lineCount);

	}

}
