package com.dblp2graph.common;

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
import javax.persistence.JoinColumn;

@Entity(name = "Author")
@Table(name = "dblp_authors", catalog = "dblp_rdbms")
public class Author implements Serializable {

	private static final long serialVersionUID = 1L;

	// author's id
	private int authorId;

	// author's fullname
	private String author;

	// author's aliases
	private String authorAliases;

	// first year publishing
	private int startYear;

	// last year publishing
	private int endYear;

	// total publishes over years
	private int pubCount;

	// homepage's key in DBLP
	private String hpDblpKey;

	// set of publishes (written by author)
	private Set<Publish> publishes = new HashSet<Publish>(0);

	public Author() {
		super();
	}

	public Author(int authorId, String author, String authorAliases, int startYear, int endYear, int pubCount,
			String hpDblpKey, Set<Publish> publishes) {
		super();
		this.authorId = authorId;
		this.author = author;
		this.authorAliases = authorAliases;
		this.startYear = startYear;
		this.endYear = endYear;
		this.pubCount = pubCount;
		this.hpDblpKey = hpDblpKey;
		this.publishes = publishes;
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
	public String getAuthorAliases() {
		return authorAliases;
	}

	public void setAuthorAliases(String authorAliases) {
		this.authorAliases = authorAliases;
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

	@Column(name = "pub_count", nullable = false)
	public int getPubCount() {
		return pubCount;
	}

	public void setPubCount(int pubCount) {
		this.pubCount = pubCount;
	}

	@Column(name = "hp_dblp_key", nullable = false)
	public String getHpDblpKey() {
		return hpDblpKey;
	}

	public void setHpDblpKey(String hpDblpKey) {
		this.hpDblpKey = hpDblpKey;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "dblp_author_pub_maps", catalog = "dblp_rdbms", joinColumns = {
			@JoinColumn(name = "author_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id", nullable = false, updatable = false) })
	public Set<Publish> getPublishes() {
		return publishes;
	}

	public void setPublishes(Set<Publish> publishes) {
		this.publishes = publishes;
	}

	@Override
	public String toString() {
		return "Author's id: \t" + this.authorId + "\r\n" + "Author's name: \t" + this.author + "\r\n"
				+ "Pub's count: \t" + this.pubCount + "\r\n";
	}

}
