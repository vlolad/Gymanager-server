package ru.gymanager.server.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

//Created by: PavelProjects

//TODO refactor: maybe need class/token builder and Issuer field in JWT for future auth system
@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 21600000; //6 hours

    private final UserDetailsService userDetailsService;
    private final String secret;

    @Autowired
    public JwtTokenUtil(UserDetailsService userDetailsService,
                        Environment env) {
        this.userDetailsService = userDetailsService;
        this.secret = env.getProperty("JWT_SECRET", "littlesecret");
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(String login) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        Objects.requireNonNull(userDetails);
        return generateToken(userDetails);
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public UserDetails validateToken(String token) {
        if (StringUtils.isBlank(token)) {
            throw new JwtException("Token is missing!");
        }

        String login = getUsernameFromToken(token);
        if (StringUtils.isBlank(login)) {
            throw new JwtException("Wrong user in token");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        if (validateToken(token, userDetails)) {
            return userDetails;
        } else {
            throw new JwtException("Wrong user in token");
        }
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
