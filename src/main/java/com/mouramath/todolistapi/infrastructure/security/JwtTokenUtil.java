package com.mouramath.todolistapi.infrastructure.security;


import com.mouramath.todolistapi.infrastructure.exception.JwtTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final long TOKEN_VALIDITY_DURATION = 60 * 60 * 24 * 7;

    private static final String TOKEN_ISSUER = "todo-list-api";

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;


    @Value("$jwt.secret")
    private String jwtSecret;

    //Methods para geração de tokens

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TOKEN_VALIDITY_DURATION);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer(TOKEN_ISSUER)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SIGNATURE_ALGORITHM)
                .compact();
    }


    //Methods para validação de tokens

    public boolean validateToken(String token, UserDetails userDetails){
        try{
            final String username = extractUsername(token);
            return username.equals(userDetails.getUsername())
                    && !isTokenExpired(token);
        } catch(JwtTokenException e){
            logger.error("Token validation failed!", e);
            return false;
        }
    }

    public boolean canTokenBeProcessed(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch(SignatureException e){
            logger.debug("Invalid JWT signature", e);
            throw JwtTokenException.invalidToken("Invalid signature", e);
        } catch(MalformedJwtException e){
            logger.debug("Invalid JWT token", e);
            throw JwtTokenException.invalidToken("Malformed token", e);
        } catch(ExpiredJwtException e){
           logger.debug("Expired JWT token", e);
           throw JwtTokenException.expiredToken(e);
        } catch(UnsupportedJwtException e){
            logger.debug("Unsupported JWT token", e);
            throw JwtTokenException.invalidToken("Unsupported token", e);
        } catch(IllegalArgumentException e){
            logger.debug("JWT claims string is empty", e);
            throw JwtTokenException.invalidToken("Token claims string is empty", e);
        }
    }

    private boolean isTokenExpired(String token){
        final Date expiration = extractExpirationDate(token);
        return expiration.before(new Date());
    }

    //Methods para extração de informações


    public String extractUsername(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
            } catch(Exception e){
                throw new JwtTokenException("Failed to extract username from token", e);
            }
        }

    public Date extractExpirationDate(String token){
        try{
            return extractClaim(token, Claims::getExpiration);
        }catch(Exception e){
            throw new JwtTokenException("Failed to extract expiration date from token", e);
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch(Exception e) {
            throw new JwtTokenException("Failed to extract claims from token", e);
        }
    }

    private Key getSigningKey(){
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}







