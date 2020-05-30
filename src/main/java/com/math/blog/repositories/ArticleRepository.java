package com.math.blog.repositories;

import com.math.blog.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "SELECT * FROM article a " +
            "WHERE article_id IN (" +
            "SELECT ar.article_id FROM article ar, tagging a_t, tag t " +
            "WHERE ar.article_id = a_t.article_id " +
            "AND t.tag_id = a_t.tag_id AND t.slug = ?1) ORDER BY a.create_date DESC", nativeQuery = true)
    List<Article> findByTagSlug(String slug);

}
