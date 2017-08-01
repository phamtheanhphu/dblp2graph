package com.dblp2graph.tool.crawler.handler;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.dblp2graph.ORM.common.entity.publication.ORMPublicationData;
import com.dblp2graph.tool.crawler.WebFileDownloader;

public class DigLibCrawlerHandler {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	public DigLibCrawlerHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static ORMPublicationData proceed(Document doc, String outputFolderPath) throws IOException {

		if (doc != null) {

			if (doc.select(".simple-item-view-description > div").text().toString() != null) {

				ORMPublicationData publishData = new ORMPublicationData();
				publishData.setDomDontent(doc.select(".simple-item-view-description > div").text().toString());
				publishData.setAccessUrl(doc.baseUri());
				return publishData;

			}

		}

		return null;

	}

}
