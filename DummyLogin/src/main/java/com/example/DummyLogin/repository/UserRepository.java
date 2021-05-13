package com.example.DummyLogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.DummyLogin.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByuserNameAndPassword(String username,String password);

}
