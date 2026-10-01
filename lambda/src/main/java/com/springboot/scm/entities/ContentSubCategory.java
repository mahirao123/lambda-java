package com.springboot.scm.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

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
public class ContentSubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

<<<<<<< HEAD
    private boolean enable=false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ContentCategory category;

    @Builder.Default
    @OneToMany(
            mappedBy = "subCategory",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Article> articles = new ArrayList<>();
=======
    @Builder.Default
    private boolean enable = false;


    // =========================================================
    // CATEGORY
    // =========================================================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "category_id",
        nullable = false
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ContentCategory category;


    // =========================================================
    // ARTICLES
    // =========================================================

    @Builder.Default
    @OneToMany(
        mappedBy = "subCategory",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Article> articles = new ArrayList<>();

>>>>>>> f773d2d (first commit)
}