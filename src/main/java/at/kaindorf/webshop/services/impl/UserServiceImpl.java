package at.kaindorf.webshop.services.impl;

import at.kaindorf.webshop.repositories.UserRepository;
import at.kaindorf.webshop.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Project: Intro_JWT_5CHIF_2024
 * Created by: SF
 * Date: 11.11.2024
 * Time: 11:21
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
  @Autowired
  private UserRepository userRepo;
  @Override
  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("user " + username + " not found"));
      }
    };
  }
}
