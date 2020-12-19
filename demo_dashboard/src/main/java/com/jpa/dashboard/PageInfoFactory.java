package com.jpa.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.jpa.dashboard.model.PageInfo;

@Component
public class PageInfoFactory {

	private final int pageUnit = 5;
	
	public List<PageInfo> generate(Page info){
		
		List<PageInfo> list = new ArrayList();
		
		int pageNumber = info.getPageable().getPageNumber();
		int totalPage = info.getTotalPages();
		
		list.add(setInfo("<<", 0, ""));
		list.add(setInfo("<", (pageNumber / pageUnit) * pageUnit - 1
				, (pageNumber / pageUnit) * pageUnit - 1 > 0 ? "" : "disabled"));
		
		for(int i=0; i<pageUnit; i++) {
			int valuePage = pageNumber - (pageNumber % pageUnit) + i;
			String coverPage = String.valueOf(valuePage + 1);
			if(valuePage == pageNumber) {
				list.add(setInfo(coverPage, valuePage, "active"));
			}else {
				boolean active = valuePage < totalPage;
				list.add(setInfo(coverPage, active ? valuePage : pageNumber
						, active ? "" : "disabled"));
			}
		}
		
		list.add(setInfo(">", (pageNumber / pageUnit) * pageUnit + pageUnit
				, (pageNumber / pageUnit) * pageUnit + pageUnit <= totalPage-1 ? "" : "disabled"));
		list.add(setInfo(">>", totalPage-1, ""));
		
		return list;
	}
	
	private PageInfo setInfo(String cover, int page, String enabled) {
		return PageInfo.builder()
				.cover(cover)
				.value(page)
				.enabled(enabled)
				.build();
	}
}
