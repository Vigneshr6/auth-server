package com.vignesh.springbackendapp.repository;

import com.vignesh.springbackendapp.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findOneByUsernameOrEmail(String username,String email);
}
