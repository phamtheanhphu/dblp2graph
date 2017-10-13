package com.dblp2graph.main.test;

import java.io.File;

import com.dblp2graph.adapter.pdf.PdfAdapter;
import com.dblp2graph.adapter.pdf.entity.PdfDocument;
import com.dblp2graph.adapter.text.TextFileAdapter;

public class TestReadPDFFile {

	private static PdfAdapter pdfAdapter = new PdfAdapter();
	private static TextFileAdapter textFileAdapter = new TextFileAdapter();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dirRootPath = "E:\\Neo4J\\download\\download_pdf";
		String outputRootPath = "E:\\Neo4J\\download\\text";

		int limitNumberOfFile = 100000;

		File[] files = new File(dirRootPath).listFiles();
		int docCount = 0;
		for (File docFile : files) {
			if (docCount < limitNumberOfFile) {
				if (docFile.isFile()) {
					try {

						String outputDocumentFilePath = outputRootPath + "\\"
								+ docFile.getName().replace(".pdf", ".txt");

						System.out.println("Document -> [" + docCount + "] !");
						PdfDocument pdfDocument = pdfAdapter.readFile(docFile.getAbsolutePath());
						// System.out.println(pdfDocument.getDocContent());

						textFileAdapter.writeToDataStringFile(pdfDocument.getDocContent(), outputDocumentFilePath);

						docCount++;
						
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} else {
				break;
			}

		}

	}

}
