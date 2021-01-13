package com.example.config.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.domain.Board;
import com.example.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	Page<Comment> findByBoardOrderByGroups(Board board, Pageable pageable);
	Long countByIndent(int indent);

}
