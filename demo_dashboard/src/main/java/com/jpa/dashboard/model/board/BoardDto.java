package com.jpa.dashboard.model.board;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BoardDto {

	private String seq;
	private String title;
	private String writer;
	private LocalDateTime createdDate;
	private Long viewCount;
	private String contents;
	
}
