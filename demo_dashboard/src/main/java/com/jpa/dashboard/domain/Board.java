package com.jpa.dashboard.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long seq;
	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String writer;
	@Column(nullable = false)
	private String contents;
	@Column(nullable = false)
	private LocalDateTime createdDate;
	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
	List<Comment> comments = new ArrayList<>();
	
	
	//== 생성자 메소드==//
	public static Board createBoard(String title, String writer, String contents) {
		Board board = new Board();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContents(contents);
		board.setCreatedDate(LocalDateTime.now());
		
		return board;
	}
}
