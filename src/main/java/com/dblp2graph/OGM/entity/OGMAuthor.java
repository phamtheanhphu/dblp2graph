package com.dblp2graph.OGM.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label = "Author")
public class OGMAuthor implements Serializable {

	private static final long serialVersionUID = 1L;

	@GraphId
	private Long id;

	@Index(unique = true, primary = true)
	@Property(name = "author_id")
	private int authorId;

	@Property(name = "name")
	private String author;

	@Property(name = "all_aliases")
	private String allAliases;

	@Property(name = "pub_count")
	private int pubCount;

	@Property(name = "avg_pub_count")
	private int avgPubCount;

	@Property(name = "start_year")
	private int startYear;

	@Property(name = "end_year")
	private int endYear;

	@Property(name = "num_sources")
	private int numSources;

	@Property(name = "hp_dblp_key")
	private String hpDblpKey;

	@Property(name = "hp_ee")
	private String hpEE;

	@Property(name = "hp_publisher")
	private String hpPublisher;

	// set of publishes (written by author)
	@Relationship(type = "AUTHOR_OF", direction = Relationship.OUTGOING)
	private Set<OGMPublication> authorOfPubs = new HashSet<OGMPublication>(0);

	public OGMAuthor() {
		super();
	}

	public OGMAuthor(Long id, int authorId, String author, String allAliases, int pubCount, int avgPubCount,
			int startYear, int endYear, int numSources, String hpDblpKey, String hpEE, String hpPublisher,
			Set<OGMPublication> authorOfPubs) {
		super();
		this.id = id;
		this.authorId = authorId;
		this.author = author;
		this.allAliases = allAliases;
		this.pubCount = pubCount;
		this.avgPubCount = avgPubCount;
		this.startYear = startYear;
		this.endYear = endYear;
		this.numSources = numSources;
		this.hpDblpKey = hpDblpKey;
		this.hpEE = hpEE;
		this.hpPublisher = hpPublisher;
		this.authorOfPubs = authorOfPubs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAllAliases() {
		return allAliases;
	}

	public void setAllAliases(String allAliases) {
		this.allAliases = allAliases;
	}

	public int getPubCount() {
		return pubCount;
	}

	public void setPubCount(int pubCount) {
		this.pubCount = pubCount;
	}

	public int getAvgPubCount() {
		return avgPubCount;
	}

	public void setAvgPubCount(int avgPubCount) {
		this.avgPubCount = avgPubCount;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public int getNumSources() {
		return numSources;
	}

	public void setNumSources(int numSources) {
		this.numSources = numSources;
	}

	public String getHpDblpKey() {
		return hpDblpKey;
	}

	public void setHpDblpKey(String hpDblpKey) {
		this.hpDblpKey = hpDblpKey;
	}

	public String getHpEE() {
		return hpEE;
	}

	public void setHpEE(String hpEE) {
		this.hpEE = hpEE;
	}

	public String getHpPublisher() {
		return hpPublisher;
	}

	public void setHpPublisher(String hpPublisher) {
		this.hpPublisher = hpPublisher;
	}

	public Set<OGMPublication> getAuthorOfPubs() {
		return authorOfPubs;
	}

	public void setAuthorOfPubs(Set<OGMPublication> authorOfPubs) {
		this.authorOfPubs = authorOfPubs;
	}

}
