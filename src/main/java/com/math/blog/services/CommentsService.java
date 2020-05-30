package com.math.blog.services;

import com.math.blog.models.Comment;
import com.math.blog.repositories.CommentsRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {

    private CommentsRepository commentsRepository;

    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }
    public void save(Comment comment){
        commentsRepository.saveAndFlush(comment);
    }
}
