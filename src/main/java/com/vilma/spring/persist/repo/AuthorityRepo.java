package com.vilma.spring.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vilma.spring.persist.entity.Authority;



public interface AuthorityRepo extends JpaRepository<Authority, Long> {

}