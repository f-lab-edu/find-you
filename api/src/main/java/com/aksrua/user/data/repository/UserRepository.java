package com.aksrua.user.data.repository;

import com.aksrua.user.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Boolean existsByEmail(String email);
}
