package com.sanjay.kfh.security;

import com.sanjay.kfh.utils.KfhConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Sanjay Vishwakarma
 */
@Component
public class KfhJwtToken implements Serializable {
    private static final long serialVersionUID = -2550185165674L;
    public static final long JWT_TOKEN_VALIDITY_time = KfhConstant.TOCKEN_VALIDATION_TIME;


    @Value("${kfh.jwt.secret}")
    private String secret;

    //retrieve username from jwt token
    public String getStudentNameFromToken(String token) {
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
    // the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

   // token expiration details
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for Student
    public String generateToken(UserDetails kfhStudentDetailsImpl) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, kfhStudentDetailsImpl.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY_time * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails kfhStudentDetailsImpl) {
        final String studentname = getStudentNameFromToken(token);
        return (studentname.equals(kfhStudentDetailsImpl.getUsername()) && !isTokenExpired(token));
    }
}
