/**
 * 
 */
package com.fii.backendapp.service;

import java.util.List;

import com.fii.backendapp.domain.Role;
import com.fii.backendapp.domain.User;

/**
 * @author Andrei
 */
public interface UserService {
	User saveUser(User user);
	Role saveRole(Role role);
	void addRoleToUser(String username, String roleName);
	User getUser(String username);
	List<User> getUsers();
}
