package com.cuongpn.entity;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;




@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)

public abstract class BaseComment extends AuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2147483647,columnDefinition="Text")
    private String content;


}
