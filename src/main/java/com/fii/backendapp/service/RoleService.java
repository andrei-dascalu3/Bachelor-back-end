package com.fii.backendapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fii.backendapp.dao.RoleDao;
import com.fii.backendapp.entity.Role;

@Service
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;
	
	public Role createNewRole(Role role) {
		return roleDao.save(role);
	}
}
