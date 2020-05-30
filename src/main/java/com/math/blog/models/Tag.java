package com.math.blog.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Integer id;

    @Column(name = "label")
    @Size(max=15, message = "Слишком длинное название для тега")
    @NotNull
    private String label;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Column(name = "slug")
    @Size(max=12, message = "Слишком длинное название для сокращения")
    @NotNull
    private String slug;

    public Tag() {
    }

    public Tag(String label) {
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    @ManyToMany(mappedBy = "tags")
    private Set<Article> articles;
}
