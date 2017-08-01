package com.dblp2graph.ORM.common.entity.publication;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity(name = "PublicationData")
@Table(name = "dblp_publish_data", catalog = "dblp_rdbms")
public class ORMPublicationData implements Serializable {

	private static final long serialVersionUID = 1L;

	// publish's id
	private int id;

	// publish's id
	private int pubId;

	// publish's dom (html) raw content
	@Lob
	private String domDontent;

	// publish's pdf download file name
	private String downloadFileName;

	// publish's access url
	private String accessUrl;

	public ORMPublicationData() {
		super();
	}

	public ORMPublicationData(int id, int pubId, String domDontent) {
		super();
		this.id = id;
		this.pubId = pubId;
		this.domDontent = domDontent;
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

	@Column(name = "pub_id", unique = true, nullable = false, length = 10)
	public int getPubId() {
		return pubId;
	}

	public void setPubId(int pubId) {
		this.pubId = pubId;
	}

	@Column(name = "dom_content", nullable = true)
	@Type(type = "text")
	public String getDomDontent() {
		return domDontent;
	}

	public void setDomDontent(String domDontent) {
		this.domDontent = domDontent;
	}

	@Column(name = "download_file_name", nullable = true)
	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	@Column(name = "access_url", nullable = true)
	public String getAccessUrl() {
		return accessUrl;
	}

	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}

}
