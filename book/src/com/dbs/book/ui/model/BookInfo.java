package com.dbs.book.ui.model;

import java.io.Serializable;
import java.util.List;

public class BookInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String summary;
	private String coverUrl;
	private String txtUrl;
	private String count;//阅读数
	private String type;//所属类别
	private String chapterNum;//章数
	
	private List<ContentsInfo> contents;
	private boolean isDownloaded;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public String getTxtUrl() {
		return txtUrl;
	}

	public void setTxtUrl(String txtUrl) {
		this.txtUrl = txtUrl;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<ContentsInfo> getContents() {
		return contents;
	}

	public void setContents(List<ContentsInfo> contents) {
		this.contents = contents;
	}

	public String getChapterNum() {
		return chapterNum;
	}

	public void setChapterNum(String chapterNum) {
		this.chapterNum = chapterNum;
	}

	public boolean isDownloaded() {
		return isDownloaded;
	}

	public void setDownloaded(boolean isDownloaded) {
		this.isDownloaded = isDownloaded;
	}
	
	
	
}
