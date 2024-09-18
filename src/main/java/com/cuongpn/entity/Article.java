package com.cuongpn.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Article")
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Article {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false,unique = true, length = 300)
    private String slug;

    private String summary;
    @Column(length = 2147483647,columnDefinition="Text")
    private String content;

    private String imageUrl;
    @Column
    private Long views;
    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;
    @LastModifiedDate
    @Column(name ="updated_at")
    private Date updatedAt;

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
    private Set<ArticleComment> comments = new HashSet<>();



}
