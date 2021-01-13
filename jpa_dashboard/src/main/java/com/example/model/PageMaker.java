package com.example.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString(exclude = "pageList")
@Slf4j
public class PageMaker<T> {

	private Page<T> result;
	
	private Pageable prevPage;
	private Pageable nextPage;
	
	private int currentPageNum;
	private int totalPageNum;
	
	private Pageable currentPage;
	
	private List<Pageable> pageList;
	
	public PageMaker(Page<T> result) {
		this.result = result;
		this.currentPage = result.getPageable();
		this.currentPageNum = currentPage.getPageNumber() + 1;
		this.totalPageNum = result.getTotalPages();
		this.pageList = new ArrayList<>();
		calcPages();
	}

	private void calcPages() {
		int tempEndNum = (int) (Math.ceil(this.currentPageNum/10.0) * 10);	//소수가 있으면 반올림하는 함수로 현재 페이지 단위의 마지막 숫자를 계산한다.
		int startNum = tempEndNum - 9;	//현제는 10단위로 페이지 단위를 만들었기 때문에 -9를 해 시작하는 기준을 설정해준다.
		Pageable startPage = this.currentPage;
		
		// 각 페이지 단위의 첫 번째로 세팅해주기 위한 반복문(5페이지 일때는 첫 페이지가 1, 15페이지 일때는 첫 페이지가 11)
		for(int i = startNum; i < this.currentPageNum; i++) {
			startPage = startPage.previousOrFirst();
		}
		//페이지가 일의 자리 숫자일 때는 prevPage에 null이 담긴다.
		this.prevPage = startPage.getPageNumber() <= 0 ? null : startPage.previousOrFirst();
		
		//tempEndNum이 총 페이지 수보다 클 경우 tempEndNum을 총 페이지 수로 설정하고, nextPage에 null을 담는다.
		if(this.totalPageNum < tempEndNum) {
			tempEndNum = this.totalPageNum;
			this.nextPage = null;
		}
		
		//기본 페이지 사이즈만큼 각각의 페이지 정보를 pageList에 담는다.
		for(int i = startNum; i <= tempEndNum; i++) {
			pageList.add(startPage);
			startPage = startPage.next();
		}
		
		//위의 반복문으로 tempEndNum보다 1만큼 더 커진 startPage가 totalPageNum보다 크면 null을 담고, 
		//totalPageNum보다 작으면 tempEndNum보다 1만큼 더 크게 설정된 startPage의 정보를 nextPage에 담는다.
		this.nextPage = startPage.getPageNumber() + 1 < totalPageNum ? startPage : null;
		
	}
	
}
