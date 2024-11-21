package at.kaindorf.webshop.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Project: Intro_JWT_5CHIF_2024
 * Created by: SF
 * Date: 21.11.2024
 * Time: 08:21
 */
@Component
@Slf4j
public class JwtUnauthorizedEndpoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException authException) throws IOException, ServletException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
    responseBody.put("error", authException.getMessage());
    responseBody.put("path", request.getRequestURL());

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(response.getWriter(),responseBody);
  }
}
