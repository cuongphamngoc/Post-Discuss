package com.cuongpn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 65535,columnDefinition="Text")
    private String content;
    private LocalDateTime timestamp;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User sender;

    private String type;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "replied_id")
    private Message replied;



}