package com.tech.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.blog.payload.ApiResponse;
import com.tech.blog.payload.CategoryDto;
import com.tech.blog.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/createCategory")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {

		CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);

		return new ResponseEntity<CategoryDto>(createdCategory, HttpStatus.CREATED);
	}

	@PutMapping("/{CategoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("CategoryId") Long categoryId) {

		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);

		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
	}

	@GetMapping("/{CategoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("CategoryId") Long categoryId) {

		CategoryDto categoryDto = this.categoryService.getCategoryById(categoryId);

		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);

	}

	@GetMapping("/getAllCategories")
	ResponseEntity<List<CategoryDto>> getAllCategories() {

		List<CategoryDto> allCategories = this.categoryService.getAllCategories();

		return ResponseEntity.ok(allCategories);

	}

	@DeleteMapping("/{CategoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("CategoryId") Long categoryId) {

		this.categoryService.deleteCategory(categoryId);

		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);
	}

}
