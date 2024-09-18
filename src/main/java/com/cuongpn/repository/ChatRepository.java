package com.cuongpn.repository;

import com.cuongpn.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface ChatRepository extends JpaRepository<Message,Long> {
    List<Message> findByOrderByTimestampDesc();

    @Query("SELECT m FROM Message m LEFT JOIN FETCH m.replied r WHERE m.timestamp >= :replyTime OR r.id = :replyToId ORDER BY m.timestamp DESC")
    List<Message> findMessagesFromReplyTime(@Param("replyTime") LocalDateTime replyTime, @Param("replyToId") Long replyToId);

}
