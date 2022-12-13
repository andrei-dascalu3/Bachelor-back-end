package com.fii.backendapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fii.backendapp.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, String>{

}
