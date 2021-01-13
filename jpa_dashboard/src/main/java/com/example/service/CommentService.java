package com.example.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.config.repository.BoardRepository;
import com.example.config.repository.CommentRepository;
import com.example.domain.Board;
import com.example.domain.Comment;
import com.example.model.comments.CommentDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {
	
	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;
	
	public Page<CommentDto> findAll(Long boardSeq, Pageable pageable){
		Board board = boardRepository.findById(boardSeq).get();
		Page<CommentDto> comments = commentRepository.findByBoardOrderByGroups(board, pageable).map(CommentDto::new);
		
		return comments; 
	}
	
	@Transactional
	public void save(Long seq, CommentDto dto, Principal principal) {
		Long groups = null;
		int indent;
		
		if(dto.getGroups() == null) {
			groups = commentRepository.countByIndent(0);
			indent = 0;
		} else {
			groups = dto.getGroups();
			indent = 1;
		}
		
		Board findBoard = boardRepository.findById(seq).get();
		Comment comment = Comment.createComment(principal.getName(), dto.getContents(), groups, indent, findBoard);
		
		commentRepository.save(comment);
	}
	
	@Transactional
	public void update(CommentDto dto) {
		Comment findComment = commentRepository.findById(Long.valueOf(dto.getSeq())).get();
		findComment.setContents(dto.getContents());
	}
	
	@Transactional
	public void delete(Long seq){
		commentRepository.deleteById(seq);
	}
	
	

}
