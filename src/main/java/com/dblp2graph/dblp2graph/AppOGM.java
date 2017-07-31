package com.dblp2graph.dblp2graph;

import com.dblp2graph.OGM.entity.Publication;
import com.dblp2graph.OGM.service.PublicationService;
import com.dblp2graph.OGM.service.impl.PublicationServiceImpl;

public class AppOGM {

	private static PublicationService publicationService = new PublicationServiceImpl();

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		/*Iterable<Publication> pubs = publicationService.findPubByDBLPKey("journals/acta/HuangL87");
		for(Publication pub : pubs) {
			System.out.println(pub.getTitle());
		}*/
		
		Publication pub = publicationService.findOneByDBLPKey("journals/acta/HuangL87");
		if(pub!=null) {
			System.out.println(pub.getTitle());
		}
		

	}

}
