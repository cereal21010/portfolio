package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.example.config.repository.BoardRepository;
import com.example.config.repository.CommentRepository;
import com.example.config.repository.MemberRepository;
import com.example.domain.Board;
import com.example.domain.Comment;
import com.example.domain.Member;
import com.example.model.comments.CommentDto;
import com.example.service.BoardService;
import com.example.service.CommentService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class JpaDashboardApplicationTests {

	@Autowired
	MemberRepository memberRepository;
	@Autowired
	BoardService boardService;
	@Autowired
	CommentService commentService;
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	BoardRepository boardRepository;
	
//	@Test
//	@Rollback(false)
//	void memberData() {
//		for(int i = 0; i < 5; i++) {
//			Member member = Member.createMember("testUser0"+i, "테스터"+i, "123123", "MEMBER");
//			memberRepository.save(member);
//		}
//	}
	
//	@Test
//	@Rollback(false)
//	void BaordData() {
//		for(int m = 0; m < 5; m++) {
//			for(int i = 0; i < 10; i++) {
//				Board board = Board.createBoard("test Dashboard 0"+i, "testUser0"+m, "이 글은 테스트를 위해 "+"testUser0"+m+"가 작성한 게시글 입니다.");
//				Long dboardSeq = boardService.save(board);
//			}
//		}
//	}

//	@Test
//	@Rollback(false)
//	@Transactional
//	void commnetData() {
//		for(int b = 7; b < 57; b++) {
//			for(int m = 9; m >= 0; m--) {
//				CommentDto dto = new CommentDto();
//				dto.setWriter("testUser0"+m);
//				dto.setContents("Commnet Test ..."+m);
//				
//				Long groups = null;
//				int indent;
//				
//				if(dto.getGroups() == null || dto.getGroups() == "") {
//					groups = commentRepository.countByIndent(0);
//					indent = 0;
//				} else {
//					groups = Long.valueOf(dto.getGroups());
//					indent = 1;
//				}
//				
//				Board findBoard = boardRepository.findById(Long.valueOf(b)).get();
//				Comment comment = Comment.createComment(dto.getWriter(), dto.getContents(), groups, indent, findBoard);
//				
//				commentRepository.save(comment);
//			}
//		}
//	}
}