/**
 * 
 */
package com.fii.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fii.backendapp.model.user.Role;

/**
 * @author Andrei
 */
public interface RoleRepository extends JpaRepository<Role, Long>{
	Role findByName(String name);
}
