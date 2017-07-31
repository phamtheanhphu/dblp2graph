package com.dblp2graph.OGM.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.neo4j.driver.internal.spi.Connection;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Publication {

	@GraphId
	private Long id;

	@Property(name = "pub_id")
	private int pubId;
	
	@Property(name = "dblp_key")
	private String dblpKey;
	
	@Property(name = "title")
	private String title;
	
	@Property(name = "source")
	private String source;
	
	@Property(name = "source_id")
	private String sourceId;
	
	@Property(name = "series")
	private String series;
	
	@Property(name = "year")
	private int year;
	
	@Property(name = "type")
	private String type;
	
	@Property(name = "volume")
	private String volume;
	
	@Property(name = "number")
	private String number;
	
	@Property(name = "month")
	private String month;
	
	@Property(name = "pages")
	private String pages;
	
	@Property(name = "ee")
	private String ee;
	
	@Property(name = "ee_uniq")
	private int eeUniq;
	
	@Property(name = "ee_PDF")
	private String eePDF;
	
	@Property(name = "url")
	private String url;
	
	@Property(name = "publisher")
	private String publisher;
	
	@Property(name = "isbn")
	private String isbn;
	
	@Property(name = "crossref")
	private String crossRef;
	
	@Property(name = "titleSignature")
	private String titleSignature;
	
	@Property(name = "doi")
	private String doi;
	
	@Property(name = "doi_uniq")
	private int doiUniq;
	
	@Property(name = "mdate")
	private Date mDate;

	@Relationship(type = "CITE_TO", direction = Relationship.INCOMING)
	private List<Publication> citedPubs = new ArrayList<Publication>();
	
	@Relationship(type = "REFER_TO", direction = Relationship.OUTGOING)
	private List<Publication> referToPubs = new ArrayList<Publication>();
	
	public Publication() {
		super();
	}

	public Publication(Long id, int pubId, String dblpKey, String title, String source, String sourceId, String series,
			int year, String type, String volume, String number, String month, String pages, String ee, int eeUniq,
			String eePDF, String url, String publisher, String isbn, String crossRef, String titleSignature, String doi,
			int doiUniq, Date mDate) {
		super();
		this.id = id;
		this.pubId = pubId;
		this.dblpKey = dblpKey;
		this.title = title;
		this.source = source;
		this.sourceId = sourceId;
		this.series = series;
		this.year = year;
		this.type = type;
		this.volume = volume;
		this.number = number;
		this.month = month;
		this.pages = pages;
		this.ee = ee;
		this.eeUniq = eeUniq;
		this.eePDF = eePDF;
		this.url = url;
		this.publisher = publisher;
		this.isbn = isbn;
		this.crossRef = crossRef;
		this.titleSignature = titleSignature;
		this.doi = doi;
		this.doiUniq = doiUniq;
		this.mDate = mDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPubId() {
		return pubId;
	}

	public void setPubId(int pubId) {
		this.pubId = pubId;
	}

	public String getDblpKey() {
		return dblpKey;
	}

	public void setDblpKey(String dblpKey) {
		this.dblpKey = dblpKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getEe() {
		return ee;
	}

	public void setEe(String ee) {
		this.ee = ee;
	}

	public int getEeUniq() {
		return eeUniq;
	}

	public void setEeUniq(int eeUniq) {
		this.eeUniq = eeUniq;
	}

	public String getEePDF() {
		return eePDF;
	}

	public void setEePDF(String eePDF) {
		this.eePDF = eePDF;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getCrossRef() {
		return crossRef;
	}

	public void setCrossRef(String crossRef) {
		this.crossRef = crossRef;
	}

	public String getTitleSignature() {
		return titleSignature;
	}

	public void setTitleSignature(String titleSignature) {
		this.titleSignature = titleSignature;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public int getDoiUniq() {
		return doiUniq;
	}

	public void setDoiUniq(int doiUniq) {
		this.doiUniq = doiUniq;
	}

	public Date getmDate() {
		return mDate;
	}

	public void setmDate(Date mDate) {
		this.mDate = mDate;
	}

	public List<Publication> getCitedPubs() {
		return citedPubs;
	}

	public void setCitedPubs(List<Publication> citedPubs) {
		this.citedPubs = citedPubs;
	}

	public List<Publication> getReferToPubs() {
		return referToPubs;
	}

	public void setReferToPubs(List<Publication> referToPubs) {
		this.referToPubs = referToPubs;
	}
	
	
}
