package com.tech.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.print.attribute.standard.Media;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tech.blog.config.AppConstants;
import com.tech.blog.entity.Post;
import com.tech.blog.entity.User;
import com.tech.blog.exception.ResourceNotFoundException;
import com.tech.blog.payload.ApiResponse;
import com.tech.blog.payload.PostDto;
import com.tech.blog.payload.PostResponse;
import com.tech.blog.service.FileService;
import com.tech.blog.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path; 

	@PostMapping("user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("userId") Long userId,
			@PathVariable Long categoryId){
		
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/Posts")
	public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable Long userId) {
		
		List<PostDto> postDtos = this.postService.getPostByUserId(userId);
		return ResponseEntity.ok(postDtos);
	}
	
	@GetMapping("/Category/{categoryId}/Posts")
	public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable Long categoryId) {
		
		List<PostDto> postDtos = this.postService.getPostByCategoryId(categoryId);
		return ResponseEntity.ok(postDtos);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "offset", defaultValue = AppConstants.OFFSET , required = false) Integer offset,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY , required = false) String sortBy,
			@RequestParam(value = "sortOrder", defaultValue = AppConstants.SORT_ORDER , required = false) String sortOrder) {
		
		PostResponse allPost = this.postService.getAllPost(offset,pageSize,sortBy,sortOrder);
		
		return ResponseEntity.ok(allPost);
	}
	
	@GetMapping("/{postId}/post")
	public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
		
		PostDto postDto = this.postService.getPostById(postId);
		
		return ResponseEntity.ok(postDto);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId){
		
		this.postService.deletePost(postId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully",true), HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Long postId){
		
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	
	@GetMapping("/searchPosts/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keyword){
		
		List<PostDto> searchedPosts = this.postService.searchPostByTitle(keyword);
		
		return new ResponseEntity<List<PostDto>>(searchedPosts, HttpStatus.OK);
	}
	
	@PostMapping("uploadImage/{postId}")
	public ResponseEntity<PostDto> uploadImage(
			@RequestParam("image") MultipartFile image, @PathVariable Long postId) throws IOException{
				
		PostDto post = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		post.setImageName(fileName);
		PostDto updatedPost = this.postService.updatePost(post, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
		
	}
	
	@GetMapping("downloadImage/{imageName}")
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException{
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
}
