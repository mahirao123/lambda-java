package com.springboot.scm.services;

import java.util.List;
import java.util.Optional;

import com.springboot.scm.entities.ContentSubCategory;

public interface ContentSubCategoryService {
	
	ContentSubCategory saveSubCategory(ContentSubCategory subCategory);
	
	ContentSubCategory updateSubCategory(ContentSubCategory subCategory);
	
	Optional<ContentSubCategory> getById(Long id);
	
List<ContentSubCategory>	getByCategoryId(Long categoryId);
List<ContentSubCategory>	getAll();

List<ContentSubCategory>	searchByNameIgnoreCaseOrderByNameAsc(String name);

void deleteSubCategory(Long id);

}
