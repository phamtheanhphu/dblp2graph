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

public class IGIGlobalCrawlerHandler {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	public IGIGlobalCrawlerHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static ORMPublicationData proceed(Document doc, String outputFolderPath) throws IOException {

		if (doc != null) {

			if (doc.select("#ctl00_ctl00_cphMain_cphSection_lblAbstract") != null) {

				ORMPublicationData publishData = new ORMPublicationData();
				publishData.setDomDontent(doc.select("#ctl00_ctl00_cphMain_cphSection_lblAbstract").text().toString()
						.replaceAll("Abstract", "").toString());
				publishData.setAccessUrl(doc.baseUri());
				return publishData;

			}

		}

		return null;

	}

}
