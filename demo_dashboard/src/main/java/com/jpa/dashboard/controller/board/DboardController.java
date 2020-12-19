package com.jpa.dashboard.controller.board;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.omg.CORBA.BAD_INV_ORDER;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.jpa.dashboard.PageInfoFactory;
import com.jpa.dashboard.domain.Board;
import com.jpa.dashboard.domain.Comment;
import com.jpa.dashboard.model.PageDto;
import com.jpa.dashboard.model.PageMaker;
import com.jpa.dashboard.model.board.BoardDto;
import com.jpa.dashboard.model.commend.CommentDto;
import com.jpa.dashboard.repository.BoardRepository;
import com.jpa.dashboard.repository.CommentRepository;
import com.jpa.dashboard.service.CommentService;
import com.jpa.dashboard.service.DboardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping(value = "/board", method= {RequestMethod.GET, RequestMethod.POST})
public class DboardController {

	private final DboardService dboardService;
	private final CommentService commentService;
	
	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;
	private final PageInfoFactory pageInfoFactory;
	
	@GetMapping
	public <T> String home(Model model, Principal principal, 
			@PageableDefault(sort = "seq", direction =  Sort.Direction.DESC, size = 10, page = 0) Pageable pageable
			, @RequestParam(value = "searchType", required = false) String searchType
			, @RequestParam(value = "searchValue", required = false) String searchValue) 
	{
		log.info("==home controller==");
		System.out.println(pageable.getPageSize() + " : " + pageable.getPageNumber());
		
		Page<Board> dboards;
		
		if(searchType == null || searchValue == null || searchValue == "" || searchValue == "") {
			dboards = dboardService.findDboards(pageable);
		} else {
			if(searchType.equals("title")) {
				dboards = boardRepository.findByTitleContainingIgnoreCase(searchValue, pageable);
			} else if(searchType.equals("writer")) {
				dboards = boardRepository.findByWriterContainingIgnoreCase(searchValue, pageable);
			}else {
				dboards = dboardService.findDboards(pageable);
			}
		}
		model.addAttribute("loginCheck",loginCheck(principal));
		model.addAttribute("dboards", dboards);
		model.addAttribute("page", pageInfoFactory.generate(dboards));

		return "home";
	}
	
	@GetMapping("/boardWriter")
	public String boardWriter(Model model, Principal principal) {
		log.info("real boardWriter controller");
		model.addAttribute("loginCheck",loginCheck(principal));
		model.addAttribute("userId", principal.getName());
		return "board/boardwriter";
	}
	
	@PostMapping("/writerAction")
	public String writerAction(@ModelAttribute Board dto, Principal principal) {

		Board board = Board.createBoard(dto.getTitle(), principal.getName(), dto.getContents());
		Long dboardSeq = dboardService.saveDboard(board);
		
		return "redirect:/board";
	}
	
	@GetMapping("/boardViewer/{seq}")
	public String boardViewer(Model model, @PathVariable Long seq, Principal principal
			,@PageableDefault(sort = "createdDate", direction =  Sort.Direction.ASC, size = 10, page = 0) Pageable pageable) {
		Board viewDboard = dboardService.viewDboard(seq);
		Page<Comment> comments = commentRepository.findByBoardOrderByGroups(viewDboard, pageable);
		String sWriterCheck = (principal.getName().equals(viewDboard.getWriter())) ? "" : "none";
//		List<String> lsCommentWriterCheck = commentService.commentWriterCheck(comments.getContent(), principal.getName());
		model.addAttribute("loginCheck",loginCheck(principal));
		model.addAttribute("writerCheck", sWriterCheck);
//		model.addAttribute("CommentWriterCheck", lsCommentWriterCheck);
		model.addAttribute("loginUser", principal.getName());
		model.addAttribute("viewDboard", viewDboard);
		model.addAttribute("comments", comments);
		model.addAttribute("page", pageInfoFactory.generate(comments));
		
		return "board/boardViewer";
	}
	
	@GetMapping("/boardEdit/{seq}")
	public String boardEdit(Model model, @PathVariable Long seq, Principal principal) {
		
		Board findBoard = boardRepository.findById(seq).get();
		BoardDto dto = new BoardDto();
		dto.setSeq(String.valueOf(findBoard.getSeq()));
		dto.setTitle(findBoard.getTitle());
		dto.setWriter(findBoard.getWriter());
		dto.setCreatedDate(findBoard.getCreatedDate());
		dto.setContents(findBoard.getContents());
		
		model.addAttribute("boardDto", dto);
		model.addAttribute("userId", principal.getName());
		
		return "board/boardwriter";
	}
	
	@PostMapping("/updateWriter/{seq}")
	public String updateWriter(Model model, @PathVariable Long seq, BoardDto dto, Principal principal) {
		dboardService.updateDboard(seq, dto);
		
		return "redirect:/board/boardViewer/" + seq;
	}
	
	@PostMapping("/boardDelete/{seq}")
	public String boardDelete(Model model, @PathVariable Long seq) {
		Board findBoard = boardRepository.findById(seq).get();
		boardRepository.delete(findBoard);
		
		return "redirect:/board";
	}
	
	
	@PostMapping("/commentAction/{seq}")
	public String commentAction(@PathVariable Long seq , @ModelAttribute CommentDto dto, Principal principal) {
		Long groups = null;
		int indent;
		
		if(dto.getGroups() == null || dto.getGroups() == "") {
			groups = commentRepository.countByIndent(0);
			indent = 0;
		} else {
			groups = Long.valueOf(dto.getGroups());
			indent = 1;
		}
		
		Board findBoard = boardRepository.findById(seq).get();
		Comment comment = Comment.createComment(principal.getName(), dto.getContents(), groups, indent, findBoard);
		
		commentRepository.save(comment);
		
		return "redirect:/board/boardViewer/"+seq;
	}
	
	@PostMapping("/commentDelete")
	public String commentDelete(@RequestBody Map<String, String> seqMap) {
		Long cSeq = Long.valueOf(seqMap.get("cSeq"));
		Long bSeq = Long.valueOf(seqMap.get("bSeq"));
		Comment findComment = commentRepository.findById(cSeq).get();
		commentRepository.delete(findComment);
		return "redirect:/board/boardViewer/"+bSeq;
	}
	
	@PostMapping("/commentUpdate/{seq}")
	public String commentUpdate(@PathVariable Long seq, @ModelAttribute CommentDto dto) {
		commentService.commentUpdate(dto);
		
		return "redirect:/board/boardViewer/"+seq;
	}
	
	private Long loginCheck(Principal principal) {
		if(principal == null) {
			return null;
		}else {
			return 1L;
		}
	}
	
}
