package com.jpa.dashboard.model.commend;

import java.time.LocalDateTime;

import com.jpa.dashboard.domain.Comment;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentDto {

	String seq;
	String groups;
	String indent;
	LocalDateTime createdDate;
	String writer;
	String contents;
	String writerCheck;

}
