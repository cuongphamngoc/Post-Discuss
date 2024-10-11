package com.cuongpn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@NamedEntityGraph(
        name = "answerDetail",
        attributeNodes = {
                @NamedAttributeNode("createdBy"),
                @NamedAttributeNode("comments"),
                @NamedAttributeNode("votes")
        }
)
public class Answer extends Post {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

}
