package com.dblp2graph.tool.crawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.dblp2graph.common.publication.PublicationData;
import com.dblp2graph.tool.crawler.handler.AISCrawlerHandler;
import com.dblp2graph.tool.crawler.handler.DigLibCrawlerHandler;
import com.dblp2graph.tool.crawler.handler.IEEPCrawlerHandler;
import com.dblp2graph.tool.crawler.handler.IGIGlobalCrawlerHandler;
import com.dblp2graph.tool.crawler.handler.LinkingHubElsevierCrawlerHandler;
import com.dblp2graph.tool.crawler.handler.SpringerCrawlerHandler;

public class AbstractWebCrawler {

	private static final String SPINGER_URL_PATTERN = "https://link.springer.com";
	private static final String LinkingHubElsevier_URL_PATTERN = "http://linkinghub.elsevier.com/retrieve";
	private static final String AIS_URL_PATTERN = "http://aisel.aisnet.org";
	private static final String DIGLIB_URL_PATTERN = "http://diglib.eg.org/handle";
	private static final String IGIGLOBAL_URL_PATTERN = "https://www.igi-global.com";

	// private static final String IEEP_URL_PATTERN =
	// "http://ieeexplore.ieee.org";

	public AbstractWebCrawler() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static PublicationData proceed(String dblpDOIUrl, String outputFolderPath) {

		try {

			Document doc = Jsoup.connect(dblpDOIUrl).followRedirects(true).get();
			String pageUrl = doc.baseUri();

			System.out.println("Processing pageUrl -> [" + pageUrl + "]");

			PublicationData publishData = new PublicationData();
			publishData.setAccessUrl(pageUrl);

			if (pageUrl.startsWith(SPINGER_URL_PATTERN)) {
				publishData = SpringerCrawlerHandler.proceed(doc, outputFolderPath);
			} else if (pageUrl.startsWith(LinkingHubElsevier_URL_PATTERN)) {
				publishData = LinkingHubElsevierCrawlerHandler.proceed(doc, outputFolderPath);
			} else if (pageUrl.startsWith(AIS_URL_PATTERN)) {
				publishData = AISCrawlerHandler.proceed(doc, outputFolderPath);
			} else if (pageUrl.startsWith(DIGLIB_URL_PATTERN)) {
				publishData = DigLibCrawlerHandler.proceed(doc, outputFolderPath);
			} else if (pageUrl.startsWith(IGIGLOBAL_URL_PATTERN)) {
				publishData = IGIGlobalCrawlerHandler.proceed(doc, outputFolderPath);
			}

			return publishData;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
