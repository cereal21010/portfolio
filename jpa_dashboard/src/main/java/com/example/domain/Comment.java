package com.example.domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long seq;
	private Long groups = null;
	private int indent; //들여쓰기
	private String writer;
	private LocalDateTime createdDate;
	private String contents;
	@ManyToOne(cascade = {
			CascadeType.REFRESH, CascadeType.DETACH
			, CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	Board board;
	
	
	//== 생성자 메소드==//
	public static Comment createComment(String writer, String contents, Long groups, int indent, Board board) {
		Comment comment = new Comment();
		comment.setWriter(writer);
		comment.setContents(contents);
		comment.setGroups(groups);
		comment.setIndent(indent);
		comment.setBoard(board);
		comment.setCreatedDate(LocalDateTime.now());
		
		return comment;
	}
	
}
