package com.cuongpn.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("ANSWER")

@NamedEntityGraph(
        name = "answerDetail",
        attributeNodes = {
                @NamedAttributeNode("createdBy"),
                @NamedAttributeNode("comments"),
                @NamedAttributeNode("votes")
        }
)
public class Answer extends Content{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

}
