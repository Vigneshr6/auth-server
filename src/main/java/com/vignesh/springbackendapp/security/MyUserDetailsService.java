package com.vignesh.springbackendapp.security;

import com.vignesh.springbackendapp.model.User;
import com.vignesh.springbackendapp.repository.UserRepository;
import com.vignesh.springbackendapp.service.UserNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @SneakyThrows
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findOneByUsernameOrEmail(username, username)
            .orElseThrow(UserNotFoundException::new);
    return user;
  }
}
