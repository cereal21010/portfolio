package com.jpa.dashboard;
 
import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.dashboard.domain.Board;
import com.jpa.dashboard.domain.Comment;
import com.jpa.dashboard.domain.Member;
import com.jpa.dashboard.repository.BoardRepository;
import com.jpa.dashboard.repository.CommentRepository;
import com.jpa.dashboard.repository.MemberRepository;
import com.jpa.dashboard.service.DboardService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class DemoDashboardApplicationTests {

	@Autowired
	DboardService dboardService; 
	@Autowired BoardRepository boardRepository;
	@Autowired CommentRepository commentRepository;
	
	@Test
	@Rollback(false)
	void contextLoads() {
//		UserInfo userInfo = new UserInfo();
//		userInfo.setId("testUser");
//		userInfo.setName("sy");
//		
//		UserInfo saveUser = userRepository.save(userInfo);
//		
//		UserInfo findUser = userRepository.findById(saveUser.getSeq()).get();
//		 
//		Assertions.assertEquals(findUser.getId(), saveUser.getId(), "같지 않다");
//		Assertions.assertEquals(findUser.getName(), saveUser.getName(), "같지 않다");
//		Assertions.assertEquals(findUser, saveUser, "같지 않다");
		
		
		
//		Board board =  new Board();
//		board.setTitle("test title");
//		board.setWriter("test writer");
//		board.setContents("test contents");
//		board.setCreatedDate(LocalDateTime.now());
		
//		Long saveSeq = dboardService.saveDboard(board);
		
//		assertEquals(dboardInfo, dboardService.viewDboard(saveSeq));
//		Assertions.assertEquals(board, dboardService.viewDboard(saveSeq), "같지 않다");
//		Board findBoard = boardRepository.findById(2718L).get();
		IntStream.range(1, 334).forEach(i -> {
//			Board board = Board.createBoard("Sample Board title "+i, "tuser1010", "Content Sample ..." + i + " of Board ");
//			boardRepository.save(board);
//			Comment comment = Comment.createComment("tuser1010", "Content Sample ..." + i + " of Comment ", Long.valueOf(i), 0, findBoard);
//			commentRepository.save(comment);
		});
		
	}
	
	
//	@Test
//	public void testList1() {
//		Pageable pageable = PageRequest.of(0, 20, Direction.DESC,"seq");
//		
//		Page<Board> result = boardRepository.findAll(pageable);
//	}

}
