package com.tech.blog.payload;

import com.tech.blog.entity.Post;
import com.tech.blog.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

	private Long id;
	
	private String content;
	
}
