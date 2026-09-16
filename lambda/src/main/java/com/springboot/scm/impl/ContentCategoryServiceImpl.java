package com.springboot.scm.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.scm.entities.ContentCategory;
import com.springboot.scm.repositories.ContentCategoryRepository;
import com.springboot.scm.services.ContentCategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContentCategoryServiceImpl
        implements ContentCategoryService {

	@Autowired
    private  ContentCategoryRepository categoryRepository;
	

    @Override
    public ContentCategory save(ContentCategory category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<ContentCategory> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public ContentCategory findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Category not found"));
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

	@Override
	public List<ContentCategory> searchByDay(String day) {
		
		return categoryRepository.findByDayIgnoreCase(day);
	}

	@Override
	public ContentCategory searchByDate(Date date) {
		
		return categoryRepository.findByDate(date);
	}

	@Override
	public ContentCategory updateCategory(ContentCategory category) {
		
		return categoryRepository.save(category);
	}

	@Override
	public boolean isExistContent(Date date) {
		
		return categoryRepository.existsByDate(date);
	}

	@Override
	public List<ContentCategory> getAllCategoriesByDate() {
		
		return categoryRepository.findAllByOrderByDateAsc();
	}
}