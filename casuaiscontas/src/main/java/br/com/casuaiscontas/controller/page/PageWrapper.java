package br.com.casuaiscontas.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {

	private static final int PAGES_LIMIT = 10;
	private Page<T> page;
	private UriComponentsBuilder uriBuilder;
	
	public PageWrapper(Page<T> page, HttpServletRequest request) {
		this.page = page;
		this.uriBuilder = ServletUriComponentsBuilder.fromRequest(request);
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
		return uriBuilder.replaceQueryParam("page", page).build(true).encode().toUriString();
	}
	
	public String urlOrder(String propertie) {
		UriComponentsBuilder uriBuilderOrder = UriComponentsBuilder.fromUriString(this.uriBuilder.build(true).encode().toUriString());
		String sortValue = String.format("%s,%s", propertie, invertDirection(propertie));
	
		return uriBuilderOrder.replaceQueryParam("sort", sortValue).build(true).encode().toUriString();
	}

	public String invertDirection(String propertie) {
		String direction = "asc";
		
		Order order = this.page.getSort() != null ? page.getSort().getOrderFor(propertie) : null;
		if(order != null) {
			direction = order.isAscending() ? "desc" : direction;
		}
		
		return direction;
	}
	
	public boolean isDesc(String propertie) {
		return this.invertDirection(propertie).equals("asc");
	}
	
}
