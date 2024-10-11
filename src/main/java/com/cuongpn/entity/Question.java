package com.cuongpn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
        name = "Question.detail",
        attributeNodes = {
                @NamedAttributeNode("comments"),
                @NamedAttributeNode("votes"),
                @NamedAttributeNode("createdBy"),
        }

)
public class Question extends Post  {

    private String title;
    @Column(nullable = false,unique = true, length = 300)
    private String slug;
    @OneToMany(mappedBy = "question",orphanRemoval = true,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Answer> answers = new HashSet<>();
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "accepted_answer_id")
    private Answer acceptedAnswer;
}
