package com.tech.blog.payload;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.tech.blog.entity.Category;
import com.tech.blog.entity.Comment;
import com.tech.blog.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
	
	@NotEmpty
	private Long postId;
	
	@NotEmpty
	@Size(min = 4, message = "title must be of atleast 4 charaters")
	private String title;
	
	@NotEmpty
	private String content;
	
	private String imageName;
	
	private Date createdDate;
	
	private CategoryDto category;
	
	private UserDto user;	
	
	

	

}
