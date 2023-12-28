package com.user.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.user.springsecurity.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	RoleEntity findByName(String name);
	
}
