package com.springboot.scm.services;

import java.sql.Date;
import java.util.List;

import com.springboot.scm.entities.ContentCategory;

public interface ContentCategoryService {

    ContentCategory save(ContentCategory category);
    
    ContentCategory updateCategory(ContentCategory category);

  

    ContentCategory findById(Long id);
    
    List<ContentCategory> getAll();
    
    List<ContentCategory> searchByDay(String day);
    
   List<ContentCategory> getAllCategoriesByDate();
    
    ContentCategory searchByDate(Date date);
    
    boolean isExistContent(Date date);
    
    void delete(Long id);
}