package com.ivan.pazar.persistence.repository;

import com.ivan.pazar.domain.model.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    Page<Message> findAllBySenderUsername(String senderUsername, Pageable pageable);

    Page<Message> findAllByReceiverUsername(String receiverUsername, Pageable pageable);
}
