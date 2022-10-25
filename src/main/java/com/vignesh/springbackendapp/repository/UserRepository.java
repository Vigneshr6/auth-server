package com.vignesh.springbackendapp.repository;

import com.vignesh.springbackendapp.model.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
  Optional<User> findOneByUsernameOrEmail(String username, String email);
}
