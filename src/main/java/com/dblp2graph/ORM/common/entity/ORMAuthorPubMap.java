package com.dblp2graph.ORM.common.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "AuthorPubMap")
@Table(name = "dblp_author_ref_new", catalog = "dblp_rdbms")
public class ORMAuthorPubMap implements Serializable {

	private static final long serialVersionUID = 1L;

	// pub id
	private int id;

	// author's fullname
	private String author;
	
	//editor
	private int editor;
	
	//author_num
	private int author_num;

	
	public ORMAuthorPubMap() {
		super();
	}

	public ORMAuthorPubMap(int id, String author, int editor, int author_num) {
		super();
		this.id = id;
		this.author = author;
		this.editor = editor;
		this.author_num = author_num;
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
	
	@Column(name = "author")
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	@Column(name = "editor")
	public int getEditor() {
		return editor;
	}

	public void setEditor(int editor) {
		this.editor = editor;
	}
	
	
	@Column(name = "author_num")
	public int getAuthor_num() {
		return author_num;
	}

	public void setAuthor_num(int author_num) {
		this.author_num = author_num;
	}
	
	

}
