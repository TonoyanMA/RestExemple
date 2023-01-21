package com.courseexemple.sarafan.repo;

import com.courseexemple.sarafan.domen.Message;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessageRepo extends JpaRepository <Message, Long> {
}
