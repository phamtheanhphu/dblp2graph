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

public class LinkingHubElsevierCrawlerHandler {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	public LinkingHubElsevierCrawlerHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static ORMPublicationData proceed(Document doc, String outputFolderPath) throws IOException {

		if (doc != null) {
			
			if (doc.select("#redirectURL") != null) {
				
				String redirectURL = doc.select("#redirectURL").attr("value");
				if (redirectURL.contains("www.sciencedirect.com")) {
					// System.out.println(doc);
					String nextPageUrl = doc.baseUri();
					if (nextPageUrl.startsWith("http://linkinghub.elsevier.com/retrieve")) {
						nextPageUrl = nextPageUrl.replace("http://linkinghub.elsevier.com/retrieve",
								"http://www.sciencedirect.com/science/article");
						doc = Jsoup.connect(nextPageUrl).get();
						if(doc!=null) {
							ORMPublicationData publishData = new ORMPublicationData();
							publishData.setDomDontent(doc.toString());
							publishData.setAccessUrl(doc.baseUri());
							return publishData;
						} else {
							return null;
						}
						
					}
				}
			}

		}

		return null;

	}

}
