package com.springboot.scm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.scm.entities.Article;
import com.springboot.scm.repositories.ArticleRepository;
import com.springboot.scm.services.ArticleService;

@Service
public class ArticleServiceImpl
        implements ArticleService {


    @Autowired
    private ArticleRepository articleRepository;


    // =========================================================
    // SAVE
    // =========================================================

    @Override
    public Article saveArticle(Article article) {

        return articleRepository.save(article);
    }


    // =========================================================
    // UPDATE
    // =========================================================

    @Override
    public Article updateArticle(
            Long id,
            Article article) {

        Article existing = articleRepository
                .findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Article not found with id: " + id
                    )
                );


        existing.setArticleId(
                article.getArticleId()
        );

        existing.setTitle(
                article.getTitle()
        );

        existing.setSlug(
                article.getSlug()
        );

        existing.setShortDescription(
                article.getShortDescription()
        );


        existing.setContentType(
                article.getContentType()
        );

        existing.setStatus(
                article.getStatus()
        );

        existing.setBreakingNews(
                article.getBreakingNews()
        );

        existing.setFeatured(
                article.getFeatured()
        );

        existing.setPublishedAt(
                article.getPublishedAt()
        );

        existing.setScheduledAt(
                article.getScheduledAt()
        );

        existing.setMediaType(
                article.getMediaType()
        );



        existing.setImageUrl(
                article.getImageUrl()
        );

        existing.setImageCloudinaryId(
                article.getImageCloudinaryId()
        );

        existing.setCaption(
                article.getCaption()
        );

        existing.setSeoTitle(
                article.getSeoTitle()
        );

        existing.setSeoDescription(
                article.getSeoDescription()
        );

        existing.setKeywords(
                article.getKeywords()
        );

        existing.setCanonicalUrl(
                article.getCanonicalUrl()
        );

        existing.setMetaRobots(
                article.getMetaRobots()
        );

 

        existing.setUpdatedAt(
                java.time.LocalDateTime.now()
        );


        return articleRepository.save(existing);
    }


    // =========================================================
    // FIND BY ID
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public Article getArticleById(Long id) {

        return articleRepository
                .findById(id)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Article not found with id: " + id
                    )
                );
    }


    // =========================================================
    // FIND ALL
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Article> getAllArticles() {

        return articleRepository.findAll();
    }


    // =========================================================
    // DELETE
    // =========================================================

    @Override
    public void deleteArticle(Long id) {

        if (!articleRepository.existsById(id)) {

            throw new RuntimeException(
                "Article not found with id: " + id
            );
        }

        articleRepository.deleteById(id);
    }


    // =========================================================
    // FIND BY SLUG
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public Article getArticleBySlug(String slug) {

        return articleRepository
                .findBySlug(slug)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Article not found with slug: " + slug
                    )
                );
    }


    // =========================================================
    // FIND BY ARTICLE ID
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public Article getArticleByArticleId(
            String articleId) {

        return articleRepository
                .findByArticleId(articleId)
                .orElseThrow(() ->
                    new RuntimeException(
                        "Article not found with articleId: "
                        + articleId
                    )
                );
    }


    // =========================================================
    // STATUS
    // =========================================================

    @Override
    public List<Article> getArticlesByStatus(
            String status) {

        return articleRepository
                .findByStatusOrderByPublishedAtDesc(status);
    }


    // =========================================================
    // CONTENT TYPE
    // =========================================================

    @Override
    public List<Article> getArticlesByContentType(
            String contentType) {

        return articleRepository
                .findByContentTypeOrderByPublishedAtDesc(
                        contentType
                );
    }


    // =========================================================
    // SUBCATEGORY
    // =========================================================

    @Override
    public List<Article> getArticlesBySubCategory(
            Long subCategoryId) {

        return articleRepository
                .findBySubCategoryIdOrderByPublishedAtDesc(
                        subCategoryId
                );
    }


    // =========================================================
    // FEATURED
    // =========================================================

    @Override
    public List<Article> getFeaturedArticles() {

        return articleRepository
                .findByFeaturedTrueAndStatusOrderByPublishedAtDesc(
                        "PUBLISHED"
                );
    }


    // =========================================================
    // BREAKING NEWS
    // =========================================================

    @Override
    public List<Article> getBreakingNews() {

        return articleRepository
                .findByBreakingNewsTrueAndStatusOrderByPublishedAtDesc(
                        "PUBLISHED"
                );
    }


    // =========================================================
    // SEARCH
    // =========================================================

    @Override
    public List<Article> searchArticles(
            String keyword) {

        return articleRepository
                .searchArticles(keyword);
    }


    // =========================================================
    // INCREASE VIEW COUNT
    // =========================================================

    @Override
    @Transactional
    public void increaseViewCount(Long id) {

        Article article = getArticleById(id);

        Long views = article.getViewCount();

        if (views == null) {
            views = 0L;
        }

        article.setViewCount(views + 1);

        articleRepository.save(article);
    }

}
