package at.kaindorf.webshop.services;

import at.kaindorf.webshop.pojos.AuthenticationResponse;
import at.kaindorf.webshop.pojos.SigninRequest;
import at.kaindorf.webshop.pojos.SignupRequest;

/**
 * Project: Intro_JWT_5CHIF_2024
 * Created by: SF
 * Date: 18.11.2024
 * Time: 11:02
 */
public interface AuthenticationService {
  AuthenticationResponse signup(SignupRequest signupRequest);

  AuthenticationResponse signin(SigninRequest signinRequest);
}
