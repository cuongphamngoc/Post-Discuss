package com.cuongpn.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Article")
public class Article {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false,unique = true)
    private String slug;

    private String summary;

    private String content;
    @Column
    private Long views;
    @CreatedDate
    private Date created_at;
    @LastModifiedDate
    private Date updated_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @CreatedBy
    private User user;
    @ManyToMany
    @JoinTable(
            name = "article_favorites",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> favouriteBy = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name ="article_tags",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();



}
