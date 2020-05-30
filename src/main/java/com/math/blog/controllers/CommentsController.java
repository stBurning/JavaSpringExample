package com.math.blog.controllers;

import com.math.blog.models.Comment;
import com.math.blog.services.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class CommentsController {

    @Autowired
    private CommentsService commentsService;
    @PostMapping("/article/{article_id}/comment")
    public String comment(@Valid Comment comment, BindingResult bindingResult, Model model){
        if (!bindingResult.hasErrors()){
            commentsService.save(comment);
            return "redirect:/article/" + comment.getArticle().getId();
        }else {
            model.addAttribute("commentError", true);
            return "redirect:/article/" + comment.getArticle().getId();
        }

    }

}
