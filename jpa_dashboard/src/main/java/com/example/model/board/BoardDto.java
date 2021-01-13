package com.example.model.board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.domain.Board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BoardDto {

	private Long seq;
	private String title;
	private String writer;
	private String createdDate;
	private Long viewCount;
	private String contents;
	
	public BoardDto(Board entity) {
		this.seq = entity.getSeq();
		this.title = entity.getTitle();
		this.writer = entity.getWriter();
		this.createdDate = entity.getCreatedDate()
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.contents = entity.getContents();
	}
	
}
