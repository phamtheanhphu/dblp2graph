package com.dblp2graph.adapter.pdf;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import com.dblp2graph.adapter.pdf.entity.PdfDocument;


public class PdfAdapter {

	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	//private CompositeUnicode2Unicode converter = new CompositeUnicode2Unicode();
	
	public PdfAdapter() {}

	public PdfDocument readFile(String filePath) {
		
		String content = null;
		PdfDocument docData = null;
		
		try {
			PDDocument document = null;
			document = PDDocument.load(new File(filePath));
			document.getClass();
			if (!document.isEncrypted()) {
				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);
				PDFTextStripper Tstripper = new PDFTextStripper();
				content = Tstripper.getText(document);
				
				// System.out.println("Text:"+st);
				document.close();
				docData = new PdfDocument();
				String title = "";
				String author = "";
				String date = "";
				
				if(document.getDocumentInformation().getTitle()!=null) {
					title = document.getDocumentInformation().getTitle();
				}
				if(document.getDocumentInformation().getAuthor()!=null) {
					author = document.getDocumentInformation().getAuthor();
				}
				if(document.getDocumentInformation().getCreationDate()!=null) {
					Date creationDate = document.getDocumentInformation().getCreationDate().getTime();
					date = dateFormat.format(creationDate);
				}
				docData.setDocTitle(title);
				docData.setDocAuthor(author);
				docData.setDocDate(date);
				docData.setDocContent(content);
								
				//System.out.println();
				//System.out.println(document.getDocumentInformation().getCreationDate().getTime());
				return docData;
			}
			return null;
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}

}
