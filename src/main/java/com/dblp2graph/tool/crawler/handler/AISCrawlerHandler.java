package com.dblp2graph.tool.crawler.handler;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.dblp2graph.common.publication.PublicationData;
import com.dblp2graph.tool.crawler.WebFileDownloader;

public class AISCrawlerHandler {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	public AISCrawlerHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static PublicationData proceed(Document doc, String outputFolderPath) throws IOException {

		if (doc != null) {

			if (doc.select("#alpha") != null) {

				PublicationData publishData = new PublicationData();
				publishData.setDomDontent(doc.select("#alpha").toString());
				publishData.setAccessUrl(doc.baseUri());
				return publishData;

			}

		}

		return null;

	}

}
