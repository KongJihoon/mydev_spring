package com.example.demo.users.repository;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByLoginId(String loginId);

    Optional<UserEntity> findByEmail(String email);
}
