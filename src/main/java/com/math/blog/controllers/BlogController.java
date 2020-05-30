package com.math.blog.controllers;

import com.math.blog.models.Article;
import com.math.blog.repositories.ArticleRepository;
import com.math.blog.repositories.TagsRepository;
import com.math.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BlogController {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    TagsRepository tagsRepository;
    @Autowired
    UserRepository userRepository;
    @GetMapping("/")
    public String blogPage(Model model) {
        Iterable<Article> articles = articleRepository.findAll(Sort.by(Sort.Direction.DESC,"createDate"));
        model.addAttribute("articles",articles);
        model.addAttribute("tags", tagsRepository.findAll());
        return "home";
    }
    @GetMapping("/category/{slug}")
    public String blogPageByTag(@PathVariable String slug, Model model) {
        Iterable<Article> articles = articleRepository.findByTagSlug(slug);
        model.addAttribute("articles",articles);
        model.addAttribute("tags", tagsRepository.findAll());
        return "home";
    }



}