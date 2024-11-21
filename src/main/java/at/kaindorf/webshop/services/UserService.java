package at.kaindorf.webshop.services;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Project: Intro_JWT_5CHIF_2024
 * Created by: SF
 * Date: 11.11.2024
 * Time: 11:20
 */
public interface UserService {
  UserDetailsService userDetailsService();
}
