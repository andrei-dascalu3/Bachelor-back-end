/**
 * 
 */
package com.fii.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fii.backendapp.domain.User;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @author Andrei
 */
@CrossOrigin( origins = "http://localhost:4200")
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
