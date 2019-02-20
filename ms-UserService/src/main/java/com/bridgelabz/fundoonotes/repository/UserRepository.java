package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User findByEmailId(String emailId);
}
