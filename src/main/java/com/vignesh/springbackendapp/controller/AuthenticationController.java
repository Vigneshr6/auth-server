package com.vignesh.springbackendapp.controller;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.vignesh.springbackendapp.model.User;
import com.vignesh.springbackendapp.security.JwtTokenProvider;
import com.vignesh.springbackendapp.service.DuplicateUserEntryException;
import com.vignesh.springbackendapp.service.UserService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationController {

  @Autowired private UserService userService;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @Autowired private AuthenticationManager authenticationManager;

  @PostMapping(value = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity signIn(@RequestBody User user) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    User principal = (User) authentication.getPrincipal();
    String token = jwtTokenProvider.createToken(principal);
    return ResponseEntity.ok().header(AUTHORIZATION, token).body(user);
  }

  @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity signup(@RequestBody @Valid User user) throws DuplicateUserEntryException {
    log.debug("signup req : {}", user);
    userService.createUser(user);
    String token = jwtTokenProvider.createToken(user);
    return ResponseEntity.ok().header(AUTHORIZATION, token).body(user);
  }

  @GetMapping(
      value = "/isexists",
      params = {"username"})
  public Boolean existsByUsername(@RequestParam String username) {
    boolean exists = userService.existsByUsernameOrEmail(username, username);
    log.debug("check username : {} , exists : {}", username, exists);
    return exists;
  }
}
