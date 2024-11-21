package at.kaindorf.webshop.controller;

import at.kaindorf.webshop.pojos.AuthenticationResponse;
import at.kaindorf.webshop.pojos.SigninRequest;
import at.kaindorf.webshop.pojos.SignupRequest;
import at.kaindorf.webshop.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Project: Intro_JWT_5CHIF_2024
 * Created by: SF
 * Date: 18.11.2024
 * Time: 10:51
 */
@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  @PostMapping("/signup")
  public ResponseEntity<AuthenticationResponse> signup(@RequestBody SignupRequest signupRequest) {
      return ResponseEntity.ok(authenticationService.signup(signupRequest));
  }

  @PostMapping("/signin")
  public ResponseEntity<AuthenticationResponse> signin(@RequestBody SigninRequest signinRequest) {
    return ResponseEntity.ok(authenticationService.signin(signinRequest));
  }


}
