<<<<<<< HEAD

=======
>>>>>>> f773d2d (first commit)
package com.springboot.scm.entities;

import java.time.LocalDateTime;

import com.springboot.scm.employeeEntities.EmployeeDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
<<<<<<< HEAD
import lombok.NoArgsConstructor;
=======
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
>>>>>>> f773d2d (first commit)

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {

    // =========================================================
    // PRIMARY KEY
    // =========================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    // =========================================================
    // ARTICLE INFORMATION
    // =========================================================

<<<<<<< HEAD

=======
>>>>>>> f773d2d (first commit)
    private String articleId;

    private String title;

    private String slug;

    @Column(name = "shortDescription", columnDefinition = "LONGTEXT")
    private String shortDescription;

    @Lob
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;


    // =========================================================
    // CONTENT TYPE
    // =========================================================

<<<<<<< HEAD
    /**
     * NEWS
     * OPINION
     * BLOG
     * FEATURE
     * PRESS_RELEASE
     */
=======
>>>>>>> f773d2d (first commit)
    private String contentType;


    // =========================================================
    // STATUS
    // =========================================================

<<<<<<< HEAD
    /**
     * DRAFT
     * SCHEDULED
     * PUBLISHED
     * ARCHIVED
     */
=======
>>>>>>> f773d2d (first commit)
    private String status;


    // =========================================================
    // NEWS FLAGS
    // =========================================================

    @Builder.Default
    private Boolean breakingNews = false;

    @Builder.Default
    private Boolean featured = false;


    // =========================================================
    // DATE / TIME
    // =========================================================

    private LocalDateTime createdAt;

    private LocalDateTime publishedAt;

    private LocalDateTime scheduledAt;

    private LocalDateTime updatedAt;


    // =========================================================
    // MEDIA
    // =========================================================
<<<<<<< HEAD
    private String mainFrameImageUrl;// main page image url
    
    private String mainFrameCloudinaryid;
    
    private String mediaType;
    
    private String imageUrl;
    
    private String embedMainUrl;
    
    private String mainUrl;
    
=======

    private String articleNo;
    
    private String mainFrameImageUrl;

    private String mainFrameCloudinaryid;

    private String mediaType;

    private String imageUrl;

    private String embedMainUrl;

    private String mainUrl;

>>>>>>> f773d2d (first commit)
    private String subUrl1;
    private String subUrl2;
    private String subUrl3;
    private String subUrl4;

    private String imageCloudinaryId;

    private String caption;


    // =========================================================
    // SEO
    // =========================================================

    private String seoTitle;

    private String seoDescription;

    private String keywords;

    private String canonicalUrl;

    private String metaRobots;


<<<<<<< HEAD

=======
>>>>>>> f773d2d (first commit)
    // =========================================================
    // STATISTICS
    // =========================================================

    @Builder.Default
    private Long viewCount = 0L;


    // =========================================================
    // CATEGORY / SUBCATEGORY
    // =========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "sub_category_id",
        nullable = false
    )
<<<<<<< HEAD
=======
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
>>>>>>> f773d2d (first commit)
    private ContentSubCategory subCategory;


    // =========================================================
    // AUTHOR
    // =========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "author_id",
        nullable = true
    )
<<<<<<< HEAD
    private EmployeeDetails author;

}

=======
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EmployeeDetails author;

}
>>>>>>> f773d2d (first commit)
