package com.tech.blog.service.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.blog.entity.Comment;
import com.tech.blog.entity.Post;
import com.tech.blog.exception.ResourceNotFoundException;
import com.tech.blog.payload.CommentDto;
import com.tech.blog.repository.CommentRepository;
import com.tech.blog.repository.PostRepository;
import com.tech.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ModelMapper modelmapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Long postId) {

		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

		Comment comment = this.modelmapper.map(commentDto, Comment.class);

		comment.setPost(post);

		Comment savedComment = this.commentRepository.save(comment);

		return this.modelmapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Long commentId) {
		
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment Id", commentId));
		
		this.commentRepository.delete(comment);

	}

}
