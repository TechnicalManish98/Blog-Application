package com.tech.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.blog.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
