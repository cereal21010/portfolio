package com.example.model.comments;

import java.time.format.DateTimeFormatter;

import com.example.domain.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CommentDto {

	Long seq;
	Long groups;
	int indent;
	String createdDate;
	String writer;
	String contents;
	String writerCheck;
	
	public CommentDto(Comment entity) {
		this.seq = entity.getSeq();
		this.groups = entity.getGroups();
		this.indent = entity.getIndent();
		this.createdDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.writer = entity.getWriter();
		this.contents = entity.getContents();
	}
}
