package com.cuongpn.repository;

import com.cuongpn.entity.RoomChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomChatRepository extends JpaRepository<RoomChat,Long> {
}
