package com.oop.Spider.Interface;

import org.springframework.ui.Model;

import com.oop.Spider.objects.Searchable;

/** Blueprint for Search
* @version 1.0
*/
public abstract class SearchInterface {
	protected abstract String search(Searchable search, Model model);
}