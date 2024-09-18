package com.cuongpn.entity;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discuss_type")
public abstract class DiscussComment extends BaseComment{


}
