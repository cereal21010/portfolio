package com.jpa.dashboard.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.dashboard.domain.Comment;
import com.jpa.dashboard.model.commend.CommentDto;
import com.jpa.dashboard.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {
	
	private final CommentRepository commentRepository;
	
	@Transactional
	public void commentUpdate(CommentDto dto) {
		Comment findComment = commentRepository.findById(Long.valueOf(dto.getSeq())).get();
		findComment.setContents(dto.getContents());
	}
	
	public List<String> commentWriterCheck(List<Comment> comments, String loginUser) {
		List<String> lsList = new ArrayList<>();
		for(Comment comment : comments) {
			if(comment.getWriter().equals(loginUser)){
				lsList.add("");
			}else {
				lsList.add("none");
			}
		}
		return lsList;
	}

}
