package com.dblp2graph.tool.crawler.handler;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.dblp2graph.common.publish.PublishData;
import com.dblp2graph.tool.crawler.WebFileDownloader;

public class IEEPCrawlerHandler {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	public IEEPCrawlerHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static PublishData proceed(Document doc, String outputFolderPath) throws IOException {

		if (doc != null) {

			if (doc.select(".pure-g.document.stats-document.ng-isolate-scope") != null) {

				PublishData publishData = new PublishData();
				publishData.setDomDontent(doc.toString());
				publishData.setAccessUrl(doc.baseUri());
				return publishData;

			}

		}

		return null;

	}

}
