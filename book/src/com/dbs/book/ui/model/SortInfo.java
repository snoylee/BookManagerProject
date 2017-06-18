package com.dbs.book.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SortInfo implements Serializable {
	
	private String child;
	private String id;
	private String parent;
	private String name;
	
	private List<SortInfo> children = new ArrayList<SortInfo>();

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SortInfo> getChildren() {
		return children;
	}

	public void setChildren(List<SortInfo> children) {
		this.children = children;
	}

	
}
