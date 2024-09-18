package com.cuongpn.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("answer")
public class AnswerComment extends DiscussComment{
    @ManyToOne
    private Answer answer;
}
