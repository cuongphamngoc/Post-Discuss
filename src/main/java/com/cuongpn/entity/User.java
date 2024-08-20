package com.cuongpn.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Timestamp;
import java.util.*;

@Entity
@Data
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    public User(String email, String password, String name, String role) {
        this.name = name;
        this.password = password;
        this.isVerification = false;
        this.email = email;
        this.role = role;
    }
    public User(UserDetails userDetails){
        this.email = userDetails.getUsername();
        this.password = userDetails.getPassword();
        this.role = userDetails.getAuthorities().toString();
    }
    public User(String email, String password,String role){
        this.email = email;
        this.password = password;
        this.role = role;
        this.isVerification = true;
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
    private Date created_at;
    @LastModifiedDate
    private Date  updated_at;

    private String verificationToken;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Article> listOfPost;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private  List<Comment> listOfComment;
    @ManyToMany
    @JoinTable(name ="user_bookmarks",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "article_id"))
    private  Set<Article> bookmarkedArticles = new HashSet<>();

    @ManyToMany(mappedBy = "favouriteBy")
    private Set<Article> favoriteArticles = new HashSet<>();




}
