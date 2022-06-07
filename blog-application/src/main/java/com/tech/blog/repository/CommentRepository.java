package com.tech.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.blog.entity.Comment;

public interface CommentRepository  extends JpaRepository<Comment, Long>{

}
