/**
 *
 */
package com.fii.backendapp.service.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fii.backendapp.util.CustomUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fii.backendapp.domain.user.Role;
import com.fii.backendapp.domain.user.User;
import com.fii.backendapp.repository.RoleRepository;
import com.fii.backendapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

/**
 * @author Andrei
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User {} found in the database", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new CustomUser(user.getUsername(), user.getPassword(), authorities, user.getId(), user.isProfessor());
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving new user to the database");
        log.info(user.toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username);
    }

    public User getUser(Long id) {
        log.info("Fetching user with id: {}", id);
        return userRepo.findById(id).get();
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }

    @Override
    public List<User> getStudents() {
        log.info("Fetching all students");
        return userRepo.findByIsProfessor(false);
    }

    @Override
    public List<User> getProfessors() {
        log.info("Fetching all professors");
        return userRepo.findByIsProfessor(true);
    }
}
