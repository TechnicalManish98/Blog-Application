package com.tech.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.blog.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	

}
