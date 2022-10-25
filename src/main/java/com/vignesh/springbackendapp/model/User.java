package com.vignesh.springbackendapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Size(min = 8, max = 20, message = "minimum length should 8")
  @Column(unique = true)
  private String username;

  @Size(min = 8, max = 20, message = "minimum length should 8")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  @Column(unique = true)
  @Email
  private String email;

  private LocalDate dob;

  @ElementCollection
  @CollectionTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"))
  @Column(name = "ROLE")
  private List<Role> authorities;

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
