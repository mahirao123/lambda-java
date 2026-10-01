package com.springboot.scm.forms;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleForm {

	// =========================================================
	// CATEGORY / SUBCATEGORY
	// =========================================================

	@NotNull(message = "Category is required")
	private Long categoryId;

	@NotNull(message = "SubCategory is required")
	private Long subCategoryId;

	// =========================================================
	// ARTICLE INFORMATION
	// =========================================================

	private String articleId;

	private String slug;

	@NotBlank(message = "Title is required")
	private String title;

	@Column(name = "shortDescription", columnDefinition = "TEXT")
	private String shortDescription;
	@Lob
	@Column(name = "content", columnDefinition = "LONGTEXT")
	private String content;

	// =========================================================
	// CONTENT TYPE
	// =========================================================

	@NotBlank(message = "Article content type is required")
	private String contentType;

	// =========================================================
	// STATUS
	// =========================================================

	private String status;

	// =========================================================
	// DATE / TIME
	// =========================================================

	private LocalDateTime createdAt;

	private LocalDateTime publishedAt;

	private LocalDateTime updatedAt;

	// =========================================================
	// MEDIA
	// =========================================================

	private String articleNo;
	

	private MultipartFile mainFrameImageFile;
	
	private String mainFrameImagePreviewUrl;// main preview url
	
	private MultipartFile imageFile;

	private String imagePreviewUrl;
	
	private String mainUrl;

	private String subUrl1;

	private String subUrl2;

	private String subUrl3;

	private String subUrl4;

	private String caption;

	// =========================================================
	// NEWS FLAGS
	// =========================================================

	private Boolean breakingNews = false;

	private Boolean featured = false;

	// =========================================================
	// SEO
	// =========================================================

	private String seoTitle;

	private String seoDescription;

	private String keywords;

	private String canonicalUrl;

	private String metaRobots;


}