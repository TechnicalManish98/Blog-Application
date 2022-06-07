package com.tech.blog.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.tech.blog.entity.Comment;
import com.tech.blog.entity.Role;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class UserDto {

	private Long id;
	
	@NotEmpty
	@Size(min = 4, message = "Username must be of atlease 4 charaters")
	private String name;
	
	@Email(message = "Email Address is not valid !")
	private String email;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@NotEmpty
	@Size(min = 3, max = 10 , message = "password must be of minimum of 4 chars and max of 10 chars !")
	private String password;
	
	@NotEmpty
	private String about;
		
	private Set<Role> roles = new HashSet<>();
	
	
}
