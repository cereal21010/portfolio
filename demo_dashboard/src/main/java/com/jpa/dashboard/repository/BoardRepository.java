package com.jpa.dashboard.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.dashboard.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>{
	Page<Board> findByWriterContainingIgnoreCase(String writer, Pageable pageable);
	Page<Board> findByTitleContainingIgnoreCase(String title, Pageable pageable);

}
