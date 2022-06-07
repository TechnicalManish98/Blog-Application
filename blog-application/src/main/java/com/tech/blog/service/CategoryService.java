package com.tech.blog.service;

import java.util.List;

import com.tech.blog.payload.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId);
	
	CategoryDto getCategoryById(Long categoryId);
	
	List<CategoryDto> getAllCategories();
	
	void deleteCategory(Long categoryId);
}
