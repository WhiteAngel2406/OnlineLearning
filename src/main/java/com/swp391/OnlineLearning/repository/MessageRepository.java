package com.swp391.OnlineLearning.repository;

import com.swp391.OnlineLearning.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository <Message, Long>{
}
