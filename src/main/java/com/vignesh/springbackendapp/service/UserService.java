package com.vignesh.springbackendapp.service;

import com.vignesh.springbackendapp.model.Role;
import com.vignesh.springbackendapp.model.User;
import com.vignesh.springbackendapp.repository.UserRepository;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  public User createUser(User user) throws DuplicateUserEntryException {
    if (existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
      log.info("username or email already exists : {}", user);
      throw new DuplicateUserEntryException("username or email already exists");
    }
    if (user.getAuthorities() == null) {
      user.setAuthorities(Collections.singletonList(Role.ROLE_USER));
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public boolean isValidUser(String username, String password) throws UserNotFoundException {
    User user =
        userRepository
            .findOneByUsernameOrEmail(username, username)
            .orElseThrow(UserNotFoundException::new);
    return passwordEncoder.matches(user.getPassword(), password);
  }

  public User findUserById(long id) throws UserNotFoundException {
    return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
  }

  public List<User> getAllUsers() {
    return (List<User>) userRepository.findAll();
  }

  public boolean existsByUsernameOrEmail(String username, String email) {
    return userRepository.findOneByUsernameOrEmail(username, email).isPresent();
  }
}
