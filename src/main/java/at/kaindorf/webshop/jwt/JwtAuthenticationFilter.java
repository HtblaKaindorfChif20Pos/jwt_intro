package at.kaindorf.webshop.jwt;

import at.kaindorf.webshop.services.JwtService;
import at.kaindorf.webshop.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Project: Intro_JWT_5CHIF_2024
 * Created by: SF
 * Date: 14.11.2024
 * Time: 09:00
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserService userService;
  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    log.info("JWT Filter for request: " + request.getRequestURL());
    String jwtToken = request.getHeader("Authorization");
    if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
      log.info("No token in request: signin, signup, public or unauthorized");
      filterChain.doFilter(request, response);
      return;
    }
    jwtToken = jwtToken.substring(7);
    String username = jwtService.extractUsername(jwtToken);
    if (!username.isBlank()) {
      UserDetails userDetails = userService.userDetailsService().loadUserByUsername(username);
      if (jwtService.isTokenValid(jwtToken, userDetails)) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,null,
            userDetails.getAuthorities());
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        log.info("Jwt Authentication Object created");
      } else {
        log.warn("Invalid Jwt token for user: " + username);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Jwt token");
        return;
      }
    }
    log.info("Jwt filter finished");
    filterChain.doFilter(request, response);
  }
}
