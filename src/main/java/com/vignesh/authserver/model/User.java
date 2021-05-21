package com.vignesh.authserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class User {
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
    private List<Role> roles;

    public org.springframework.security.core.userdetails.User toAuthUser() {
        org.springframework.security.core.userdetails.User u = new org.springframework.security.core.userdetails.User(username,
                password,
                getRoles().stream().map(
                        r -> new SimpleGrantedAuthority(
                                r.getAuthority())).filter(Objects::nonNull).collect(Collectors.toList()));
        return u;
    }
}
