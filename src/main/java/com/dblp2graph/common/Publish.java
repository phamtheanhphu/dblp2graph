package com.dblp2graph.common;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name = "Publish")
@Table(name = "dblp_publishes", catalog = "dblp_rdbms")
public class Publish implements Serializable {

	private static final long serialVersionUID = 1L;

	// publish's id
	private int id;

	// publish - DBLP's key
	private String dblpKey;

	// publish's title
	private String title;

	// publish's DOI Url
	private String publishDOIUrl;

	// publish's download pdf file
	private String publishDOIPdfUrl;
	
	// set of reference publishes
	private Set<Publish> referPublishes = new HashSet<Publish>(0);
	
	// set of cited publishes (cited / reference to)
	private Set<Publish> citedPublishes = new HashSet<Publish>(0);

	public Publish() {
		super();
	}

	public Publish(int id, String dblpKey, String title) {
		super();
		this.id = id;
		this.dblpKey = dblpKey;
		this.title = title;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "dblp_key", unique = true, nullable = false, length = 150)
	public String getDblpKey() {
		return dblpKey;
	}

	public void setDblpKey(String dblpKey) {
		this.dblpKey = dblpKey;
	}

	@Column(name = "title", nullable = true)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "ee", nullable = true)
	public String getPublishDOIUrl() {
		return publishDOIUrl;
	}

	public void setPublishDOIUrl(String publishDOIUrl) {
		this.publishDOIUrl = publishDOIUrl;
	}

	@Column(name = "ee_PDF", nullable = true)
	public String getPublishDOIPdfUrl() {
		return publishDOIPdfUrl;
	}

	public void setPublishDOIPdfUrl(String publishDOIPdfUrl) {
		this.publishDOIPdfUrl = publishDOIPdfUrl;
	}
	
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "dblp_pub_citation_maps", catalog = "dblp_rdbms", joinColumns = {
			@JoinColumn(name = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "cited_pub_id", nullable = false, updatable = false) })
	public Set<Publish> getReferPublishes() {
		return referPublishes;
	}

	public void setReferPublishes(Set<Publish> referPublishes) {
		this.referPublishes = referPublishes;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "dblp_pub_citation_maps", catalog = "dblp_rdbms", joinColumns = {
			@JoinColumn(name = "cited_pub_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id", nullable = false, updatable = false) })
	public Set<Publish> getCitedPublishes() {
		return citedPublishes;
	}

	public void setCitedPublishes(Set<Publish> citedPublishes) {
		this.citedPublishes = citedPublishes;
	}

	@Override
	public String toString() {
		return "Pub's Id: \t" + this.id + "\r\n" + "Pub's title: \t" + this.title + "\r\n" + "DBLP's key: \t"
				+ this.dblpKey;
	}

}
