package com.math.blog.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(name = "title", nullable = false)
    @Length(min = 5, message = "*Your title must have at least 5 characters")
    @NotEmpty(message = "*Please propost_idvide title")
    private String title;

    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", nullable = false, updatable = false)
    @CreationTimestamp
    private Date createDate;

    public Article(@Length(min = 5, message = "*Your title must have at least 5 characters")
                   @NotEmpty(message = "*Please provide title") String title,
                   String body,
                   @NotNull User author) {
        this.title = title;
        this.body = body;
        this.author = author;
    }

    public Article() {
    }

    public String getIntro(){
        if(body.length() < 500){
            return body;
        }else {
            return body.substring(0, 500) + " ...";
        }

    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getAuthorUsername(){
        return author.getUsername();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author", referencedColumnName = "user_id", nullable = false)
    @NotNull
    private User author;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "tagging",
                joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @Size(max = 5, message = "Вы выбрали слишком много категорий.")
    @NotEmpty(message = "Пожалуйста, выберите от 1 до 5 категорий")
    private Set<Tag> tags;

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private Collection<Comment> comments;

}
