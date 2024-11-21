package at.kaindorf.webshop.services.impl;

import at.kaindorf.webshop.pojos.*;
import at.kaindorf.webshop.repositories.UserRepository;
import at.kaindorf.webshop.services.AuthenticationService;
import at.kaindorf.webshop.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

/**
 * Project: Intro_JWT_5CHIF_2024
 * Created by: SF
 * Date: 18.11.2024
 * Time: 11:04
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepo;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public AuthenticationResponse signup(SignupRequest signupRequest) {
    User user = User.builder()
        .firstname(signupRequest.getFirstname())
        .lastname(signupRequest.getLastname())
        .email(signupRequest.getEmail())
        .role(Role.valueOf(signupRequest.getRole()))
        .password(passwordEncoder.encode(signupRequest.getPassword()))
        .build();
    try {
      userRepo.save(user);
      String token = jwtService.generateToken(user);
      return new AuthenticationResponse(token);
    } catch (DataIntegrityViolationException e) {
      throw new RuntimeException("username already exists");   // ToDo: add execption handling
    }
  }

  @Override
  public AuthenticationResponse signin(SigninRequest signinRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
      String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());
      return new AuthenticationResponse(token);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e.getMessage());
    }
  }
}
