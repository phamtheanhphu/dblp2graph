package com.dblp2graph.OGM.entity.relation;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import com.dblp2graph.OGM.entity.OGMAuthor;
import com.dblp2graph.OGM.entity.OGMPublication;

@RelationshipEntity(type = "AUTHOR_OF")
public class AUTHOR_OF {

	@GraphId
	Long id;

	@StartNode
	OGMAuthor sourceAuthor;
	
	@EndNode
	OGMPublication targetPub;

	@Property(name = "order")
	int order;

	public AUTHOR_OF(OGMAuthor sourceAuthor, OGMPublication targetPub) {
		super();
		this.sourceAuthor = sourceAuthor;
		this.targetPub = targetPub;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OGMAuthor getSourceAuthor() {
		return sourceAuthor;
	}

	public void setSourceAuthor(OGMAuthor sourceAuthor) {
		this.sourceAuthor = sourceAuthor;
	}

	public OGMPublication getTargetPub() {
		return targetPub;
	}

	public void setTargetPub(OGMPublication targetPub) {
		this.targetPub = targetPub;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	
	

}
