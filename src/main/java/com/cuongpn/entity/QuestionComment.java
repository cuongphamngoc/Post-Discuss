package com.cuongpn.entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("question")
public class QuestionComment extends DiscussComment{
    @ManyToOne
    private Question question;
}
