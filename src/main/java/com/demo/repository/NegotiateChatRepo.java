package com.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.entity.NegotiateChatEntity;

@Repository
public interface NegotiateChatRepo extends JpaRepository<NegotiateChatEntity, Long> {
  
}