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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Article")
@Builder
@Getter
@Setter
@NamedEntityGraph(
        name = "ArticleDetail",
        attributeNodes = {
                @NamedAttributeNode("createdBy"),
                @NamedAttributeNode("votes")
        }
)
public class Article extends Post{

    @Column(nullable = false)
    private String title;
    @Column(nullable = false,unique = true, length = 300)
    private String slug;
    @Column(nullable = false,unique = true, length = 300)
    private String summary;

    private String imageUrl;
    @Column
    private Long views;


}
