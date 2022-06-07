package com.tech.blog.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech.blog.entity.Category;
import com.tech.blog.entity.Post;
import com.tech.blog.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);

	@Query(value = "select * from posts where lower(post_title) like (%:keyword%)", nativeQuery = true)
	List<Post> searchPostByTitle(@Param("keyword") String keyword);
}
