package com.dblp2graph.adapter.pdf.entity;

public class PdfDocument {
	
	private String docId;
	private String docTitle;
	private String docAuthor;
	private String docDate;
	private String docContent;
	
	public PdfDocument() {}
	
	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public String getDocAuthor() {
		return docAuthor;
	}

	public void setDocAuthor(String docAuthor) {
		this.docAuthor = docAuthor;
	}

	public String getDocDate() {
		return docDate;
	}

	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocContent() {
		return docContent;
	}

	public void setDocContent(String docContent) {
		this.docContent = docContent;
	}
	
}
