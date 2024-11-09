package com.cuongpn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
@DiscriminatorValue("QUESTION")
@AllArgsConstructor
@NoArgsConstructor
@NamedEntityGraph(
        name = "QuestionDetail",
        attributeNodes = {
                @NamedAttributeNode("comments"),
                @NamedAttributeNode("votes"),
                @NamedAttributeNode("createdBy"),
                @NamedAttributeNode("tags")
        }

)
public class Question extends Post  {

    @OneToMany(mappedBy = "question",orphanRemoval = true,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Answer> answers = new HashSet<>();
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "accepted_answer_id")
    private Answer acceptedAnswer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "question_followers"
            ,joinColumns = @JoinColumn(name="question_id")
            ,inverseJoinColumns = @JoinColumn(name="user_id")
    )
    private Set<User> questionFollowers  = new HashSet<>();

   
}
