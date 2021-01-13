package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.config.repository.BoardRepository;
import com.example.domain.Board;
import com.example.model.board.BoardDto;

@Service
@Transactional(readOnly = true)
public class BoardService {

	@Autowired
	BoardRepository boardRepository;
	
	public List<Board> findAll(){
		return boardRepository.findAll();
	}
	
	public Page<BoardDto> findAll(Pageable pageable){
		return boardRepository.findAll(pageable).map(BoardDto::new);
	}
	
	public BoardDto find(Long seq) {
		return boardRepository.findById(seq).map(BoardDto::new).get();
	}
	
	@Transactional
	public Long save(Board board) {
		return boardRepository.save(board).getSeq();
	}
	
	@Transactional
	public void update(Long seq, BoardDto dto) {
		Board findBoard = boardRepository.findById(seq).get();
		findBoard.setContents(dto.getContents());
	}
	
	@Transactional
	public void delete(Long seq) {
		boardRepository.deleteById(seq);
	}
	
}
