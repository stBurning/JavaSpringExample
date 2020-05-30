package com.math.blog.controllers;

import com.math.blog.models.Article;
import com.math.blog.models.Comment;
import com.math.blog.models.Tag;
import com.math.blog.models.User;
import com.math.blog.repositories.ArticleRepository;
import com.math.blog.repositories.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TagsRepository tagsRepository;
    @GetMapping("/add")
    public String toBlogAdd( @AuthenticationPrincipal User author, Model model) {
        Article article = new Article();
        article.setAuthor(author);
        List<Tag> tagList = tagsRepository.findAll();
        model.addAttribute("tags", tagList);
        model.addAttribute("article", article);
        return "add-article";
    }
    @PostMapping("/add")
    public String blogAdd(@Valid Article article, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            List<Tag> tagList = tagsRepository.findAll();
            model.addAttribute("tags", tagList);
            return "add-article";
        }else {
            if(article.getTags().isEmpty())
                article.setTags(Set.of(tagsRepository.getOne(6)));// Others
            articleRepository.saveAndFlush(article);
            return "redirect:/";
        }
    }

    @GetMapping(value = "/article/{id}")
    public String getPostWithId(@PathVariable Long id, @AuthenticationPrincipal User user, Model model) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if(optionalArticle.isPresent()){
            Comment comment = new Comment();
            comment.setArticle(optionalArticle.get());
            comment.setUser(user);
            model.addAttribute("comments", optionalArticle.get().getComments());
            model.addAttribute("tags", tagsRepository.findAll());
            model.addAttribute("comment", comment);
            model.addAttribute("article", optionalArticle.get());
            return "article";
        }else {
            return "home";
        }
    }
}
