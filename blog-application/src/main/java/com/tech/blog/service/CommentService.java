package com.tech.blog.service;

import com.tech.blog.entity.Post;
import com.tech.blog.payload.CommentDto;


public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto, Long postId);
	
	void deleteComment(Long commentId);

}
