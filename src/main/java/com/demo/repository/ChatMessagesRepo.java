package com.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.entity.Car;
import com.demo.entity.ChatMessages;
import com.demo.entity.NegotiateChatEntity;

@Repository
public interface ChatMessagesRepo extends JpaRepository<ChatMessages, Long> {
	
	@Query(value = "SELECT * FROM CHAT_MESSAGES WHERE USER_ID=? ORDER BY CREATED_DATE ASC", nativeQuery = true)
	 List<ChatMessages> findAllBySenderOrderByDate(long buyerId);
  
}