package com.vignesh.authserver.repository;

import com.vignesh.authserver.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findOneByUsernameOrEmail(String username,String email);
}
