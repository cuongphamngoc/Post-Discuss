package com.cuongpn.repository;


import com.cuongpn.entity.Tag;
import com.cuongpn.enums.TagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);

    Set<Tag> findByTagType(TagType tagType);
}
