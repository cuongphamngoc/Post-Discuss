package com.cuongpn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String provider;
    private String providerId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
