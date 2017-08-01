package com.dblp2graph.ORM.common.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Date;
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

import org.neo4j.ogm.annotation.Property;

@Entity(name = "Publication")
@Table(name = "dblp_publishes", catalog = "dblp_rdbms")
public class ORMPublication implements Serializable {

	private static final long serialVersionUID = 1L;

	// publish's id
	private int id;

	// publish - DBLP's key
	private String dblpKey;

	// publish's title
	private String title;

	// publish's DOI Url
	// private String publishDOIUrl;

	// publish's download pdf file
	// private String publishDOIPdfUrl;

	private String source;

	private String sourceId;

	private String series;

	private int year;

	private String type;

	private String volume;

	private String number;

	private String month;

	private String pages;

	private String ee;

	private int eeUniq;

	private String eePDF;

	private String url;

	private String publisher;

	private String isbn;

	private String crossRef;

	private String titleSignature;

	private String doi;

	private int doiUniq;

	private Date mDate;

	// set of reference publishes
	private Set<ORMPublication> referPublishes = new HashSet<ORMPublication>(0);

	// set of cited publishes (cited / reference to)
	private Set<ORMPublication> citedPublishes = new HashSet<ORMPublication>(0);

	public ORMPublication() {
		super();
	}

	public ORMPublication(int id, String dblpKey, String title) {
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

	/*
	 * @Column(name = "ee", nullable = true) public String getPublishDOIUrl() {
	 * return publishDOIUrl; }
	 * 
	 * public void setPublishDOIUrl(String publishDOIUrl) { this.publishDOIUrl =
	 * publishDOIUrl; }
	 * 
	 * @Column(name = "ee_PDF", nullable = true) public String
	 * getPublishDOIPdfUrl() { return publishDOIPdfUrl; }
	 * 
	 * public void setPublishDOIPdfUrl(String publishDOIPdfUrl) {
	 * this.publishDOIPdfUrl = publishDOIPdfUrl; }
	 */

	@Column(name = "source", nullable = true)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "source_id", nullable = true)
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	@Column(name = "series", nullable = true)
	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	@Column(name = "year", nullable = true)
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Column(name = "type", nullable = true)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "volume", nullable = true)
	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	@Column(name = "number", nullable = true)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "month", nullable = true)
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Column(name = "pages", nullable = true)
	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	@Column(name = "ee", nullable = true)
	public String getEe() {
		return ee;
	}

	public void setEe(String ee) {
		this.ee = ee;
	}

	@Column(name = "ee_uniq", nullable = true)
	public int getEeUniq() {
		return eeUniq;
	}

	public void setEeUniq(int eeUniq) {
		this.eeUniq = eeUniq;
	}

	@Column(name = "ee_PDF", nullable = true)
	public String getEePDF() {
		return eePDF;
	}

	public void setEePDF(String eePDF) {
		this.eePDF = eePDF;
	}

	@Column(name = "url", nullable = true)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "publisher", nullable = true)
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Column(name = "isbn", nullable = true)
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Column(name = "crossref", nullable = true)
	public String getCrossRef() {
		return crossRef;
	}

	public void setCrossRef(String crossRef) {
		this.crossRef = crossRef;
	}

	@Column(name = "titleSignature", nullable = true)
	public String getTitleSignature() {
		return titleSignature;
	}

	public void setTitleSignature(String titleSignature) {
		this.titleSignature = titleSignature;
	}

	@Column(name = "doi", nullable = true)
	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	@Column(name = "doi_uniq", nullable = true)
	public int getDoiUniq() {
		return doiUniq;
	}

	public void setDoiUniq(int doiUniq) {
		this.doiUniq = doiUniq;
	}

	@Column(name = "mdate", nullable = true)
	public Date getmDate() {
		return mDate;
	}

	public void setmDate(Date mDate) {
		this.mDate = mDate;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "dblp_pub_citation_maps", catalog = "dblp_rdbms", joinColumns = {
			@JoinColumn(name = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "cited_pub_id", nullable = false, updatable = false) })
	public Set<ORMPublication> getReferPublishes() {
		return referPublishes;
	}

	public void setReferPublishes(Set<ORMPublication> referPublishes) {
		this.referPublishes = referPublishes;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "dblp_pub_citation_maps", catalog = "dblp_rdbms", joinColumns = {
			@JoinColumn(name = "cited_pub_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "id", nullable = false, updatable = false) })
	public Set<ORMPublication> getCitedPublishes() {
		return citedPublishes;
	}

	public void setCitedPublishes(Set<ORMPublication> citedPublishes) {
		this.citedPublishes = citedPublishes;
	}

	@Override
	public String toString() {
		return "Pub's Id: \t" + this.id + "\r\n" + "Pub's title: \t" + this.title + "\r\n" + "DBLP's key: \t"
				+ this.dblpKey;
	}

	public com.dblp2graph.OGM.entity.OGMPublication toOGMObject() {

		com.dblp2graph.OGM.entity.OGMPublication pubOGM = new com.dblp2graph.OGM.entity.OGMPublication();

		pubOGM.setPubId(this.getId());

		if (this.dblpKey != null) {
			pubOGM.setDblpKey(this.dblpKey);
		}

		if (this.dblpKey != null) {
			pubOGM.setTitle(this.title);
		}

		if (this.dblpKey != null) {
			pubOGM.setSource(this.source);
		}

		if (this.sourceId != null) {
			pubOGM.setSourceId(this.sourceId);
		}

		pubOGM.setYear(this.year);

		if (this.type != null) {
			pubOGM.setType(this.type);
		}

		if (this.volume != null) {
			pubOGM.setVolume(this.volume);
		}

		if (this.number != null) {
			pubOGM.setNumber(this.number);
		}

		if (this.month != null) {
			pubOGM.setMonth(this.month);
		}
		
		if (this.pages != null) {
			pubOGM.setPages(this.pages);
		}
		
		if(this.ee != null) {
			pubOGM.setEe(this.ee);
		}
	
		/*if(this.eeUniq!=null) {
			pubOGM.setEeUniq(this.eeUniq);
		}*/
		
		pubOGM.setEeUniq(this.eeUniq);
		
		if(this.eePDF!=null) {
			pubOGM.setEePDF(this.eePDF);
		}
	
		if(this.url!=null) {
			pubOGM.setUrl(this.url);
		}
		
		if(this.publisher!=null) {
			pubOGM.setPublisher(this.publisher);
		}
		
		if(this.isbn!=null) {
			pubOGM.setIsbn(this.isbn);
		}
		
		if(this.crossRef!=null) {
			pubOGM.setCrossRef(this.crossRef);
		}
		
		if(this.titleSignature!=null) {
			pubOGM.setTitleSignature(this.titleSignature);
		}
		
		if(this.doi!=null) {
			pubOGM.setDoi(this.doi);
		}
	
		///pubOGM.setDoiUniq(pub.getDoiUniq());
		
		if(this.mDate!=null) {
			pubOGM.setmDate(this.mDate);
		}
		
		
		return pubOGM;

	}

}
