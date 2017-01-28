package com.vilma.spring.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vilma.spring.persist.entity.Token;

public interface TokenRepo extends JpaRepository<Token, String> {
}
