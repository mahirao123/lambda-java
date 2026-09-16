
package com.springboot.scm.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.scm.entities.Article;

public interface ArticleRepository
        extends JpaRepository<Article, Long> {


    // =========================================================
    // FIND BY ARTICLE ID
    // =========================================================

    Optional<Article> findByArticleId(String articleId);


    // =========================================================
    // FIND BY SLUG
    // =========================================================

    Optional<Article> findBySlug(String slug);


    // =========================================================
    // CHECK SLUG
    // =========================================================

    boolean existsBySlug(String slug);


    // =========================================================
    // CHECK ARTICLE ID
    // =========================================================

    boolean existsByArticleId(String articleId);


    // =========================================================
    // FIND BY STATUS
    // =========================================================

    List<Article> findByStatusOrderByPublishedAtDesc(
            String status
    );


    // =========================================================
    // FIND BY CONTENT TYPE
    // =========================================================

    List<Article> findByContentTypeOrderByPublishedAtDesc(
            String contentType
    );


    // =========================================================
    // FIND BY SUBCATEGORY
    // =========================================================

    List<Article> findBySubCategoryIdOrderByPublishedAtDesc(
            Long subCategoryId
    );


    // =========================================================
    // FEATURED ARTICLES
    // =========================================================

    List<Article> findByFeaturedTrueAndStatusOrderByPublishedAtDesc(
            String status
    );


    // =========================================================
    // BREAKING NEWS
    // =========================================================

    List<Article> findByBreakingNewsTrueAndStatusOrderByPublishedAtDesc(
            String status
    );


    // =========================================================
    // SEARCH
    // =========================================================

    @Query("""
        SELECT a
        FROM Article a
        WHERE LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(a.shortDescription)
              LIKE LOWER(CONCAT('%', :keyword, '%'))
        ORDER BY a.publishedAt DESC
    """)
    List<Article> searchArticles(
            @Param("keyword") String keyword
    );

}

