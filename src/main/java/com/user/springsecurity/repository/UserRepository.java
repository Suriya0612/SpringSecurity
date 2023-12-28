package com.user.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.user.springsecurity.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
}
