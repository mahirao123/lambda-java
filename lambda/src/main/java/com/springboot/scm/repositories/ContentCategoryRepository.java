package com.springboot.scm.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.scm.entities.ContentCategory;

public interface ContentCategoryRepository
        extends JpaRepository<ContentCategory, Long> {

	 boolean existsByDate(Date date);
    
    ContentCategory findByDate(Date date);
    
    List<ContentCategory> findAllByOrderByDateAsc();
    
    List<ContentCategory> findByDayIgnoreCase(String day);
    
    
}