package br.com.casuaiscontas.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {

	private static final int PAGES_LIMIT = 10;
	private Page<T> page;
	private UriComponentsBuilder componentsBuilder;
	
	public PageWrapper(Page<T> page, HttpServletRequest request) {
		this.page = page;
		this.componentsBuilder = ServletUriComponentsBuilder.fromRequest(request);
	}
	 
	public List<T> getContent() {
		return page.getContent();
	}
	
	public boolean isEmpty() {
		return page.getContent().isEmpty();
	}
	
	public int getCurrentPage() {
		return page.getNumber();
	}
	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public int getTotalPages() {
		return page.getTotalPages();
	}
	
	// first element
	public int getBegin() {
		return Math.max(1,  (this.getCurrentPage() - PAGES_LIMIT) + 1);
	}

	// last element
	public int getEnd() {
		return this.getTotalPages() < PAGES_LIMIT ? this.getTotalPages() : this.getBegin() + PAGES_LIMIT;		
	}
		
	public String urlToPage(int page) {
		return componentsBuilder.replaceQueryParam("page", page).build(true).encode().toUriString();
	}
	
}
