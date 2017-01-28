package com.vilma.spring.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vilma.spring.persist.entity.User;


public interface UserRepo extends JpaRepository<User, Long> {
    User findByLogin(String login);

}
