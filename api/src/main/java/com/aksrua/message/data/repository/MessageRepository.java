package com.aksrua.message.data.repository;

import com.aksrua.message.data.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
