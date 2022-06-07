package com.tech.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tech.blog.entity.Category;
import com.tech.blog.entity.Post;
import com.tech.blog.entity.User;
import com.tech.blog.payload.PostDto;
import com.tech.blog.payload.PostResponse;


public interface PostService {

	PostDto createPost(PostDto postDto, Long userId, Long categoryId);
	
	PostDto updatePost(PostDto postDto, Long postId);
	
	void deletePost(Long postId);
	
	PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);
	
	PostDto getPostById(Long postId);
	
	List<PostDto> getPostByUserId(Long userId);
	
	List<PostDto> getPostByCategoryId(Long categoryId);
	
	List<PostDto> searchPostByTitle(String keyword);
}
