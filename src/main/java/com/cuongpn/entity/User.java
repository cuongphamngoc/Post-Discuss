package com.cuongpn.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Timestamp;
import java.util.*;

@Entity
@Data
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.isActive = true;
        this.email = email;
        this.role = role;
    }
    public User(UserDetails userDetails){
        this.username = userDetails.getUsername();
        this.password = userDetails.getPassword();
        this.role = userDetails.getAuthorities().toString();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    private String username;

    private String password;
    @Column(name = "email")
    private String email;

    private String role;

    private boolean isActive;
    @CreatedDate
    private Date created_at;
    @LastModifiedDate
    private Date  updated_at;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> listOfPost;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private  List<Comment> listOfComment;




}
