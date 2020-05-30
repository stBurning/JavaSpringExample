package com.math.blog.repositories;

import com.math.blog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
}
