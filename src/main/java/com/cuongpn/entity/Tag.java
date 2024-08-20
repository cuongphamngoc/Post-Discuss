package com.cuongpn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    @Id
    Long id;

    String name;

    String description;

    @ManyToMany(mappedBy = "tags")
    private Set<Article> articles = new HashSet<>();



}
