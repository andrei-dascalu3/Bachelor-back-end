package com.fii.backendapp.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fii.backendapp.dao.UserDao;
import com.fii.backendapp.entity.JwtRequest;
import com.fii.backendapp.entity.JwtResponse;
import com.fii.backendapp.entity.User;
import com.fii.backendapp.utils.JwtUtil;

@Service
public class JwtService implements UserDetailsService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;

	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		String email = jwtRequest.getEmail();
		String password = jwtRequest.getPassword();
		authenticate(email, password);

		final UserDetails userDetails = loadUserByUsername(email);
		String newGeneratedToken = jwtUtil.generateToken(userDetails);
		User user = userDao.findById(email).get();
		return new JwtResponse(user, newGeneratedToken);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.findById(email).get();

		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					getAuthorities(user));
		} else {
			throw new UsernameNotFoundException("Email " + email + " is not valid.");
		}
	}

	private Set getAuthorities(User user) {
		Set authorities = new HashSet();

		user.getRole().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
		});

		return authorities;
	}

	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("User is disabled.");
		} catch (BadCredentialsException e) {
			throw new Exception("User has bad credentials.");
		}
	}
}
