/**
 * 
 */
package com.fii.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fii.backendapp.domain.user.User;

import java.util.List;

/**
 * @author Andrei
 */
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
	List<User> findByIsProfessor(boolean isProfessor);
}
