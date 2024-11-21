package at.kaindorf.webshop.services;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Project: Intro_JWT_5CHIF_2024
 * Created by: SF
 * Date: 14.11.2024
 * Time: 08:29
 */
public interface JwtService {
  String generateToken(UserDetails userDetails);
  boolean isTokenValid(String token, UserDetails userDetails);
  String extractUsername(String token);
}
