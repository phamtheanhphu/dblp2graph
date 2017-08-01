package com.dblp2graph.ORM.common.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.neo4j.ogm.annotation.Property;

import com.dblp2graph.OGM.entity.OGMAuthor;

import javax.persistence.JoinColumn;

@Entity(name = "Author")
@Table(name = "dblp_authors", catalog = "dblp_rdbms")
public class ORMAuthor implements Serializable {

	private static final long serialVersionUID = 1L;

	// author's id
	private int authorId;

	// author's fullname
	private String author;

	private String allAliases;

	private int pubCount;

	private int avgPubCount;

	private int startYear;

	private int endYear;

	private int numSources;

	private String hpDblpKey;

	private String hpEE;

	private String hpPublisher;

	// set of publishes (written by author)
	private Set<ORMPublication> authorOfPubs = new HashSet<ORMPublication>(0);

	public ORMAuthor() {
		super();
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "author_id", unique = true, nullable = false)
	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	@Column(name = "author", unique = true, nullable = false, length = 150)
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "all_aliases", nullable = true)
	public String getAllAliases() {
		return allAliases;
	}

	public void setAllAliases(String allAliases) {
		this.allAliases = allAliases;
	}

	@Column(name = "pub_count", nullable = true)
	public int getPubCount() {
		return pubCount;
	}

	public void setPubCount(int pubCount) {
		this.pubCount = pubCount;
	}

	@Column(name = "avg_pub_count", nullable = true)
	public int getAvgPubCount() {
		return avgPubCount;
	}

	public void setAvgPubCount(int avgPubCount) {
		this.avgPubCount = avgPubCount;
	}

	@Column(name = "start_year", nullable = true)
	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	@Column(name = "end_year", nullable = true)
	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	@Column(name = "num_sources", nullable = true)
	public int getNumSources() {
		return numSources;
	}

	public void setNumSources(int numSources) {
		this.numSources = numSources;
	}

	@Column(name = "hp_dblp_key", nullable = true)
	public String getHpDblpKey() {
		return hpDblpKey;
	}

	public void setHpDblpKey(String hpDblpKey) {
		this.hpDblpKey = hpDblpKey;
	}

	@Column(name = "hp_ee", nullable = true)
	public String getHpEE() {
		return hpEE;
	}

	public void setHpEE(String hpEE) {
		this.hpEE = hpEE;
	}

	@Column(name = "hp_publisher", nullable = true)
	public String getHpPublisher() {
		return hpPublisher;
	}

	public void setHpPublisher(String hpPublisher) {
		this.hpPublisher = hpPublisher;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "dblp_author_pub_maps", catalog = "dblp_rdbms", joinColumns = {
			@JoinColumn(name = "author_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id", nullable = false, updatable = false) })
	public Set<ORMPublication> getAuthorOfPubs() {
		return authorOfPubs;
	}

	public void setAuthorOfPubs(Set<ORMPublication> authorOfPubs) {
		this.authorOfPubs = authorOfPubs;
	}

	@Override
	public String toString() {
		return "Author's id: \t" + this.authorId + "\r\n" + "Author's name: \t" + this.author + "\r\n"
				+ "Pub's count: \t" + this.pubCount + "\r\n";
	}

	public OGMAuthor toOGMObject() {

		OGMAuthor ogmAuthor = new OGMAuthor();

		ogmAuthor.setAuthorId(this.authorId);

		if (this.author != null) {
			ogmAuthor.setAuthor(author);
		}

		if (this.allAliases != null) {
			ogmAuthor.setAllAliases(this.allAliases);
		}

		ogmAuthor.setPubCount(this.pubCount);

		ogmAuthor.setAvgPubCount(this.avgPubCount);

		ogmAuthor.setStartYear(this.startYear);

		ogmAuthor.setEndYear(this.endYear);

		ogmAuthor.setNumSources(this.numSources);

		if (this.hpDblpKey != null) {
			ogmAuthor.setHpDblpKey(this.hpDblpKey);
		}

		if (this.hpEE != null) {
			ogmAuthor.setHpEE(this.hpEE);
		}

		if (this.hpPublisher != null) {
			ogmAuthor.setHpPublisher(this.hpPublisher);
		}

		return ogmAuthor;
	}

}
