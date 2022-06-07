package com.tech.blog.service.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tech.blog.entity.Category;
import com.tech.blog.entity.User;
import com.tech.blog.exception.ResourceNotFoundException;
import com.tech.blog.payload.CategoryDto;
import com.tech.blog.payload.UserDto;
import com.tech.blog.repository.CategoryRepository;
import com.tech.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category category = this.modelMapper.map(categoryDto, Category.class);
		
		Category savedCategory = this.categoryRepository.save(category);
		
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
		
		Category category = this.categoryRepository.findById(categoryId).
				orElseThrow(() -> new ResourceNotFoundException("Category","category Id",categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category savedCategory = this.categoryRepository.save(category);

		return this.modelMapper.map(savedCategory, CategoryDto.class);

	}

	@Override
	public CategoryDto getCategoryById(Long categoryId) {
		
		Category category = this.categoryRepository.findById(categoryId).
				orElseThrow(() -> new ResourceNotFoundException("Category","category Id",categoryId));
		
		return this.modelMapper.map(category, CategoryDto.class);

	}

	@Override
	public List<CategoryDto> getAllCategories() {
		
		List<Category> Categories = this.categoryRepository.findAll();

		List<CategoryDto> allCategoryDto = Categories.stream()
				.map((category) -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		
		return allCategoryDto;
	}

	@Override
	public void deleteCategory(Long categoryId) {
		
		Category category = this.categoryRepository.findById(categoryId).
				orElseThrow(() -> new ResourceNotFoundException("Category","category Id",categoryId));
		
		this.categoryRepository.delete(category);
		
	}

}
