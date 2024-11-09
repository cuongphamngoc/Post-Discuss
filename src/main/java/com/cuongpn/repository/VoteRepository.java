package com.cuongpn.repository;

import com.cuongpn.entity.Content;
import com.cuongpn.entity.Post;
import com.cuongpn.entity.User;
import com.cuongpn.entity.Vote;
import com.cuongpn.enums.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {

    Optional<Vote> findByCreatedByAndContent(User user, Content content);

    List<Vote> findByContent_Id(Long contentId);
    Optional<Vote> findByContent_IdAndCreatedBy_Email(Long contentId, String email);

    @Query("SELECT v.content.id, SUM(CASE WHEN v.voteType = 0 THEN 1 ELSE 0 END) AS upVotes, " +
            "SUM(CASE WHEN v.voteType = 1 THEN 1 ELSE 0 END) AS downVotes " +
            "FROM Vote v WHERE v.content.id IN :contentIds GROUP BY v.content.id")
    List<Object[]> findVoteCountByContentIds(@Param("contentIds") List<Long> contentIds);

    @Query("SELECT v.content.id, v.voteType FROM Vote v WHERE v.content.id IN :contentIds AND v.createdBy.id = :userId")
    List<Object[]> findVotedCommentIdsAndTypeByUser(@Param("contentIds") List<Long> contentIds, @Param("userId") Long userId);
}
