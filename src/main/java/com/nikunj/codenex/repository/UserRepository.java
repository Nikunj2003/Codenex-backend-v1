package com.nikunj.codenex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nikunj.codenex.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
