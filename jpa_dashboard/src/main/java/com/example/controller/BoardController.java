package com.example.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
//import org.omg.CORBA.BAD_INV_ORDER;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.PageInfoFactory;
import com.example.config.repository.BoardRepository;
import com.example.config.repository.CommentRepository;
import com.example.domain.Board;
import com.example.domain.Comment;
import com.example.model.board.BoardDto;
import com.example.model.comments.CommentDto;
import com.example.service.CommentService;
import com.example.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping(value = "/board", method= {RequestMethod.GET, RequestMethod.POST})
public class BoardController {

	private final BoardService boardService;
	private final CommentService commentService;
	
	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;
	private final PageInfoFactory pageInfoFactory;
	
	@GetMapping
	public String home(Model model, Principal principal, 
			@PageableDefault(sort = "seq", direction =  Sort.Direction.DESC, size = 10, page = 0) Pageable pageable
			, @RequestParam(value = "searchType", required = false) String searchType
			, @RequestParam(value = "searchValue", required = false) String searchValue) 
	{
		log.info("==home controller==");
		
		Page<BoardDto> dboards;
		
		if(searchType == null || searchValue == null || searchValue == "" || searchValue == "") {
			dboards = boardService.findAll(pageable);
		} else {
			if(searchType.equals("title")) {
				dboards = boardRepository.findByTitleContainingIgnoreCase(searchValue, pageable).map(BoardDto::new);
			} else if(searchType.equals("writer")) {
				dboards = boardRepository.findByWriterContainingIgnoreCase(searchValue, pageable).map(BoardDto::new);
			}else {
				dboards = boardService.findAll(pageable);
			}
		}
		model.addAttribute("dboards", dboards);
		model.addAttribute("page", pageInfoFactory.generate(dboards));

		return "home";
	}
	
	@GetMapping("/boardWriter")
	public String boardWriter(Model model, Principal principal) {
		model.addAttribute("userId", principal.getName());
		
		return "board/boardwriter";
	}
	
	@PostMapping("/writerAction")
	public String writerAction(@ModelAttribute Board dto, Principal principal) {
		Board board = Board.createBoard(dto.getTitle(), principal.getName(), dto.getContents());
		Long dboardSeq = boardService.save(board);
		
		return "redirect:/board";
	}
	
	@GetMapping("/boardViewer/{seq}")
	public String boardViewer(Model model, @PathVariable Long seq, Principal principal
			,@PageableDefault(sort = "createdDate", direction =  Sort.Direction.ASC, size = 10, page = 0) Pageable pageable) {
		BoardDto viewDboard = boardService.find(seq);
		Page<CommentDto> comments = commentService.findAll(viewDboard.getSeq(), pageable);
		String sWriterCheck = (principal.getName().equals(viewDboard.getWriter())) ? "" : "none";
		model.addAttribute("writerCheck", sWriterCheck);
		model.addAttribute("viewDboard", viewDboard);
		model.addAttribute("comments", comments);
		model.addAttribute("page", pageInfoFactory.generate(comments));
		
		return "board/boardViewer";
	}
	
	@GetMapping("/boardEdit/{seq}")
	public String boardEdit(Model model, @PathVariable Long seq, Principal principal) {
		
		BoardDto dto = boardRepository.findById(seq).map(BoardDto::new).get();
		
		model.addAttribute("boardDto", dto);
		model.addAttribute("userId", principal.getName());
		
		return "board/boardwriter";
	}
	
	@PostMapping("/updateWriter/{seq}")
	public String updateWriter(Model model, @PathVariable Long seq, BoardDto dto, Principal principal) {
		boardService.update(seq, dto);
		
		return "redirect:/board/boardViewer/" + seq;
	}
	
	@PostMapping("/boardDelete")
	public String boardDelete(Model model, @RequestParam("boardDelSeq") Long seq) {
		boardService.delete(seq);
		
		return "redirect:/board";
	}
	
	
	@PostMapping("/commentAction/{seq}")
	public String commentAction(@PathVariable Long seq , @ModelAttribute CommentDto dto, Principal principal) {
		commentService.save(seq, dto, principal);
		
		return "redirect:/board/boardViewer/"+seq;
	}
	
	@PostMapping("/commentDelete")
	public String commentDelete(@RequestBody Map<String, String> seqMap) {
		Long cSeq = Long.valueOf(seqMap.get("cSeq"));
		Long bSeq = Long.valueOf(seqMap.get("bSeq"));
		commentService.delete(cSeq);
		return "redirect:/board/boardViewer/"+bSeq;
	}
	
	@PostMapping("/commentUpdate/{seq}")
	public String commentUpdate(@PathVariable Long seq, @ModelAttribute CommentDto dto) {
		commentService.update(dto);
		
		return "redirect:/board/boardViewer/"+seq;
	}

	
}
