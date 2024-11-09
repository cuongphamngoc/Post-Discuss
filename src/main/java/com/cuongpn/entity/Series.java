package com.cuongpn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@DiscriminatorValue("SERIES")
@AllArgsConstructor
@NoArgsConstructor
public class Series extends Post{


    @OneToMany(mappedBy = "series",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    Set<Article> articles = new HashSet<>();
}
