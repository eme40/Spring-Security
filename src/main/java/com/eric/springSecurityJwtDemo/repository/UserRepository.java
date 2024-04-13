package com.eric.springSecurityJwtDemo.repository;

import com.eric.springSecurityJwtDemo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

  Optional<UserEntity> findByEmail(String username);
}
