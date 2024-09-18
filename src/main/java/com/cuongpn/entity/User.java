package com.cuongpn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class User {
    public User(String email, String password, String name, String role) {
        this.name = name;
        this.password = password;
        this.isVerification = false;
        this.email = email;
        this.role = role;
        this.avatarUrl = "http://localhost:8080/public/images/default-avatar.webp";
    }
    public User(UserDetails userDetails){
        this.email = userDetails.getUsername();
        this.password = userDetails.getPassword();
        this.role = userDetails.getAuthorities().toString();
        this.avatarUrl = "http://localhost:8080/public/images/default-avatar.webp";
    }
    public User(String email, String password,String role){
        this.email = email;
        this.password = password;
        this.role = role;
        this.isVerification = true;
        this.avatarUrl = "http://localhost:8080/public/images/default-avatar.webp";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;
    @Column(name = "email")
    private String email;
    private String name;

    private String password;

    private String role;

    private boolean isVerification;
    @CreatedDate
    private LocalDateTime created_at;
    @LastModifiedDate
    private LocalDateTime  updated_at;

    private String avatarUrl;

    private String verificationToken;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Article> listOfPost;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private  List<ArticleComment> listOfComment;
    @ManyToMany
    @JoinTable(name ="user_bookmarks",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "article_id"))
    @JsonIgnore
    private  Set<Article> bookmarkedArticles = new HashSet<>();

    @ManyToMany(mappedBy = "favouriteBy")
    private Set<Article> favoriteArticles = new HashSet<>();




}
