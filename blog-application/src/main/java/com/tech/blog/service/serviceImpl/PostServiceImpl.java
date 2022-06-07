package com.tech.blog.service.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tech.blog.entity.Category;
import com.tech.blog.entity.Post;
import com.tech.blog.entity.User;
import com.tech.blog.exception.ResourceNotFoundException;
import com.tech.blog.payload.PostDto;
import com.tech.blog.payload.PostResponse;
import com.tech.blog.payload.UserDto;
import com.tech.blog.repository.CategoryRepository;
import com.tech.blog.repository.PostRepository;
import com.tech.blog.repository.UserRepository;
import com.tech.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {
		
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setCreatedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post createdPost = postRepository.save(post);
		PostDto createdPostDto = this.modelMapper.map(createdPost, PostDto.class);
		return createdPostDto;
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long postId) {
		
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepository.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
		
	}

	@Override
	public void deletePost(Long postId) {
		
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		this.postRepository.delete(post);
		
	}

	@Override
	public PostResponse getAllPost(Integer offset, Integer pageSize, String sortBy, String sortOrder) {
		
		Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable pageReq = PageRequest.of(offset, pageSize, sort);
		
		Page<Post> pagePost = this.postRepository.findAll(pageReq);

		List<Post> allPosts = pagePost.toList();
		
		List<PostDto> postDtos = allPosts.stream().map(post-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDtos);
		postResponse.setOffset(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;		
	}

	@Override
	public PostDto getPostById(Long postId) {
	
		Post post = this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post Id", postId));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		
		return postDto;
	}

	@Override
	public List<PostDto> getPostByUserId(Long userId) {
		
		User user = this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "User Id", userId));
		List<Post> userPosts = this.postRepository.findByUser(user);
		
		List<PostDto> postDtos = userPosts.stream().map(post-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		return postDtos;
	}

	@Override
	public List<PostDto> getPostByCategoryId(Long categoryId) {
		
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		List<Post> posts = this.postRepository.findByCategory(category);
		
		List<PostDto> postDtos = posts.stream().map(post-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		return postDtos;
	}

	@Override
	public List<PostDto> searchPostByTitle(String keyword) {
		
		List<Post> posts = this.postRepository.searchPostByTitle(keyword);
		List<PostDto> postDtos = posts.stream().map(post-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		return postDtos;
		
	}

}
