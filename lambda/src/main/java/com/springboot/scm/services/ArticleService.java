
package com.springboot.scm.services;

import java.util.List;

import com.springboot.scm.entities.Article;

public interface ArticleService {


    // CREATE

    Article saveArticle(Article article);


    // UPDATE

    Article updateArticle(Long id, Article article);


    // FIND BY ID

    Article getArticleById(Long id);


    // FIND ALL

    List<Article> getAllArticles();


    // DELETE

    void deleteArticle(Long id);


    // FIND BY SLUG

    Article getArticleBySlug(String slug);


    // FIND BY ARTICLE ID

    Article getArticleByArticleId(String articleId);


    // STATUS

    List<Article> getArticlesByStatus(String status);


    // CONTENT TYPE

    List<Article> getArticlesByContentType(
            String contentType
    );


    // SUBCATEGORY

    List<Article> getArticlesBySubCategory(
            Long subCategoryId
    );


    // FEATURED

    List<Article> getFeaturedArticles();


    // BREAKING NEWS

    List<Article> getBreakingNews();


    // SEARCH

    List<Article> searchArticles(String keyword);


    // INCREASE VIEW

    void increaseViewCount(Long id);

}
