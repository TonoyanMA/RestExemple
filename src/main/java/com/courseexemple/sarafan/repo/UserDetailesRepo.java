package com.courseexemple.sarafan.repo;

import com.courseexemple.sarafan.domen.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailesRepo extends JpaRepository <User, String> {
}
