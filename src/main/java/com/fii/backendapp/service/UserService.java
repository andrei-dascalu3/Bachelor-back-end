package com.fii.backendapp.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fii.backendapp.dao.RoleDao;
import com.fii.backendapp.dao.UserDao;
import com.fii.backendapp.entity.Role;
import com.fii.backendapp.entity.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User registerNewUser(User user) {
		return userDao.save(user);
	}

	public void initRolesAndUser() {
		Role adminRole = new Role();
		adminRole.setRoleName("admin");
		adminRole.setRoleDescription("Admin role");
		roleDao.save(adminRole);

		Role userRole = new Role();
		userRole.setRoleName("user");
		userRole.setRoleDescription("Default role");
		roleDao.save(userRole);
		
		User adminUser = new User();
		adminUser.setEmail("admin@mymail.com");
		adminUser.setFirstName("admin");
		adminUser.setLastName("admin");
		adminUser.setPassword(getEncodedPassword("password"));
		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);
		adminUser.setRole(adminRoles);
		userDao.save(adminUser);
	}
	
	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}
}
