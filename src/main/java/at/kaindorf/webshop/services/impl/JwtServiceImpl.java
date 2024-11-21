package at.kaindorf.webshop.services.impl;

import at.kaindorf.webshop.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * Project: Intro_JWT_5CHIF_2024
 * Created by: SF
 * Date: 14.11.2024
 * Time: 08:34
 */
@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

  @Override
  public String generateToken(UserDetails userDetails) {
    return Jwts.builder()
        .subject(userDetails.getUsername())
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000 ))
        .signWith(getSigningKey())
        .compact();
  }

  @Override
  public boolean isTokenValid(String token, UserDetails userDetails) {
    Claims claims = extractClaims(token);
    String username = userDetails.getUsername();
    Date actualDate = new Date();
    return username.equals(claims.getSubject()) && actualDate.before(claims.getExpiration());
  }

  private Claims extractClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  @Override
  public String extractUsername(String token) {
    return extractClaims(token).getSubject();
  }

  @Value("${token.signing.secret}")
  private String jwtSecret;


  /**
   * usage of HmacSHA256 Algorithm
   *
   * @return SecretKey
   */
  private SecretKey getSigningKey() {
    byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
