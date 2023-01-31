/**
 * 
 */
package com.fii.backendapp.service.user;

import java.util.List;

import com.fii.backendapp.model.user.Role;
import com.fii.backendapp.model.user.User;

/**
 * @author Andrei
 */
public interface UserService {
	User saveUser(User user);
	Role saveRole(Role role);
	void addRoleToUser(String username, String roleName);
	User getUser(Long id);
	User getUser(String username);
	List<User> getUsers();
	List<User> getProfessors();
	List<User> getStudents();
	List<Long> getProfessorIds();
}
