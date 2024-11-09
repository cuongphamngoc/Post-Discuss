package com.cuongpn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@DiscriminatorValue("ARTICLE")
@NamedEntityGraph(
        name = "ArticleDetail",
        attributeNodes = {
                @NamedAttributeNode("createdBy"),
                @NamedAttributeNode("votes"),
                @NamedAttributeNode("tags"),
                @NamedAttributeNode("comments")
        }
)

public class Article extends Post{

    @Column(length = 300)
    private String summary;

    private String imageUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "series_id")
    private Series series;
    @Column(name = "is_features")
    private boolean isFeatures = false;



}
