package com.cuongpn.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleComment extends BaseComment{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private ArticleComment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ArticleComment> replies = new HashSet<>();

    @ManyToOne
    private Article article;
}
