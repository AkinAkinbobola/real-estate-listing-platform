package com.akinbobola.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration-time}")
    private Long jwtExpiration;

    public String extractUsername (String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public <T> T extractClaim (String jwtToken, Function <Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims (String jwtToken) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(jwtToken).getBody();
    }

    private Key getSignInKey () {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid (String jwtToken, UserDetails userDetails) {
        String userEmail = extractUsername(jwtToken);
        return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired (String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration (String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    public String generateToken (UserDetails userDetails) {
        return generateToken(new HashMap <>(), userDetails);
    }

    public String generateToken (HashMap <String, Object> extraClaims, UserDetails user) {
        return buildToken(extraClaims, user, jwtExpiration);
    }

    private String buildToken (HashMap <String, Object> extraClaims, UserDetails user, Long jwtExpiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
