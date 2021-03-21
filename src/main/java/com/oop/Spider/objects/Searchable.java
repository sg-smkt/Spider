package com.oop.Spider.objects;

/**
 *Searchable object
 *@version 1.0
 */
//Acts as a model during the HTTP POST request from submitting the form from the view page
public class Searchable {
	private String search;
	
	public String getSearch() {
		return search;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}
}
