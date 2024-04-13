package com.eric.springSecurityJwtDemo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
  private final static String SECRET_KEY = "2qqA1Vo5xbV4oJ1CKjoqKsGyNl8W0J5T1pCpUoEj03Rsy70O13Z1sF59yeMc/btmUnxxQw/nbfkqAuT/PHYxvQ==";

  // extract all claims
  private Claims extractAllClaim(String token){
    return Jwts
            .parser()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
  }

  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  // extract single claim
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
    final Claims claims = extractAllClaim(token);
    return claimsResolver.apply(claims);
  }
  public String extractUserName(String token){
    return extractClaim(token, Claims::getSubject);
  }
  // generate token for claims and user details

  public String generatedToken(Map<String,Object > extractClaims, UserDetails userDetails){
    return Jwts
            .builder()
            .setClaims(extractClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 24))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
  }
  public String generateToken(UserDetails userDetails){
    return (generatedToken(new HashMap<>(), userDetails));
  }

  public Boolean isTokenValid(String token, UserDetails userDetails){
    final String username = extractUserName(token);
    return (username.equals(userDetails.getUsername())) && isTokenExpired(token);
  }

  private boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }
}
