package com.example.evaluation.User.repository;

import com.example.evaluation.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByStudentNum(String studentNum);
    void deleteUserById(Long userId);
}
