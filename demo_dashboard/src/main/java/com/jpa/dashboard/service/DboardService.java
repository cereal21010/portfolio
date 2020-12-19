package com.jpa.dashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.dashboard.domain.Board;
import com.jpa.dashboard.model.board.BoardDto;
import com.jpa.dashboard.repository.BoardRepository;

@Service
@Transactional(readOnly = true)
public class DboardService {

	@Autowired
	BoardRepository boardRepository;
	
	public List<Board> findDboards(){
		return boardRepository.findAll();
	}
	
	public Page<Board> findDboards(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional
	public Long saveDboard(Board board) {
		return boardRepository.save(board).getSeq();
	}
	
	public Board viewDboard(Long seq) {
		return boardRepository.findById(seq).get();
	}
	
	@Transactional
	public void updateDboard(Long seq, BoardDto dto) {
		Board findBoard = boardRepository.findById(seq).get();
		findBoard.setContents(dto.getContents());
	}
	
}
