package com.dblp2graph.main;

import com.dblp2graph.OGM.entity.OGMPublication;
import com.dblp2graph.OGM.service.OGMPublicationService;
import com.dblp2graph.OGM.service.impl.OGMPublicationServiceImpl;

public class AppOGM {

	private static OGMPublicationService publicationService = new OGMPublicationServiceImpl();

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		/*Iterable<Publication> pubs = publicationService.findPubByDBLPKey("journals/acta/HuangL87");
		for(Publication pub : pubs) {
			System.out.println(pub.getTitle());
		}*/
		
		OGMPublication pub = publicationService.findOneByDBLPKey("journals/acta/HuangL87");
		if(pub!=null) {
			System.out.println(pub.getTitle());
		}
		

	}

}
