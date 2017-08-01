package com.dblp2graph.ORM.common.service.dao;

import java.util.List;

import com.dblp2graph.ORM.common.entity.ORMAuthor;

public interface ORMAuthorDAO extends ORMGenericDAO<ORMAuthor, String> {

	public ORMAuthor findByName(String authorName);
	
	public ORMAuthor findAuthorById(int authorId);
	
	public List<ORMAuthor> fetchWithLimit(int startAt, int maxTakenRecordNum);

}
