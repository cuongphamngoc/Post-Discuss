package com.cuongpn.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Answer extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2147483647,columnDefinition="Text")
    private String content;
    @ManyToOne
    private Question question;
    @OneToMany(mappedBy = "answer")
    private List<AnswerComment> answerComments;

}
