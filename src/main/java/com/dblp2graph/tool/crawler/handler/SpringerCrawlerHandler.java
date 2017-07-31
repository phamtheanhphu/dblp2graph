package com.dblp2graph.tool.crawler.handler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.dblp2graph.common.publication.PublicationData;
import com.dblp2graph.tool.crawler.WebFileDownloader;

public class SpringerCrawlerHandler {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	public SpringerCrawlerHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static PublicationData proceed(Document doc, String outputFolderPath) {

		if (doc != null) {

			PublicationData publishData = new PublicationData();
			
			publishData.setDomDontent(doc.toString());
			publishData.setAccessUrl(doc.baseUri());

			Elements pdfDownloadElements = doc.select("object#test-pdf-preview");

			if (pdfDownloadElements != null) {

				String previewPdfDocFilePath = pdfDownloadElements.attr("data").replaceAll("//", "http://");
				String outputFileName = dateFormat.format(new Date()) + ".pdf";
				if(WebFileDownloader.download(previewPdfDocFilePath, outputFolderPath, outputFileName)) {
					publishData.setDownloadFileName(outputFileName);
				}
				
			}
			
			return publishData;

		}

		return null;

	}

}
