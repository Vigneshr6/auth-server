package com.vignesh.authserver.security;

import com.vignesh.authserver.model.Role;
import com.vignesh.authserver.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret:mysecretkey}")
    private String secretKey;

    @Value("#{${security.jwt.valid-for: 30} * 1000}")
    private long validFor;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("userId",user.getId());
        claims.put("auth",user.getAuthorities());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validFor);
        return Jwts.builder().setClaims(claims).setExpiration(validity).setIssuedAt(now).
        signWith(SignatureAlgorithm.HS256,secretKey)
        .compact();
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        List<Role> authorities = ((List<String>) body.get("auth")).stream().map(a -> Role.valueOf(a)).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(body.getSubject(),"", authorities);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid token");
        }
    }

}
