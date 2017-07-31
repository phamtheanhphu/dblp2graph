package com.dblp2graph.OGM.entity;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity
public class Author {

	@GraphId
	private Long id;

	@Property(name = "author_id")
	private int authorId;

	@Property(name = "full_name")
	private String fullName;

	@Property(name = "aliases")
	private String aliases;

	public Author() {
		super();
	}

	public Author(Long id, String fullName, String aliases) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.aliases = aliases;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

}
