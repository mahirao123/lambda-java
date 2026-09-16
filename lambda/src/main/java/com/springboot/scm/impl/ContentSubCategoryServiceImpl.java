package com.springboot.scm.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.scm.entities.ContentCategory;
import com.springboot.scm.entities.ContentSubCategory;
import com.springboot.scm.repositories.ContentSubCategoryRepository;
import com.springboot.scm.services.ContentCategoryService;
import com.springboot.scm.services.ContentSubCategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentSubCategoryServiceImpl implements ContentSubCategoryService{

	@Override
	public ContentSubCategory saveSubCategory(ContentSubCategory subCategory) {
	
		return contentSubCategoryRepo.save(subCategory);
	}

	
	@Autowired
	private ContentSubCategoryRepository  contentSubCategoryRepo;
	@Override
	public List<ContentSubCategory> getByCategoryId(Long categoryId) {
		
		return contentSubCategoryRepo.findByCategoryId(categoryId) ;
	}
	@Override
	public Optional<ContentSubCategory> getById(Long id) {
		
		return contentSubCategoryRepo.findById(id);
	}
	@Override
	public List<ContentSubCategory> searchByNameIgnoreCaseOrderByNameAsc(String name) {
		
		return contentSubCategoryRepo.findByNameContainingIgnoreCase(name);
	}
	@Override
	public ContentSubCategory updateSubCategory(ContentSubCategory subCategory) {

		return contentSubCategoryRepo.save(subCategory);
	}
	@Override
	public void deleteSubCategory(Long id) {
		contentSubCategoryRepo.deleteById(id);
		
	}
	@Override
	public List<ContentSubCategory> getAll() {
		
		return contentSubCategoryRepo.findAll();
	}

}
