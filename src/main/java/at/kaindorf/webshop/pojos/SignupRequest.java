package at.kaindorf.webshop.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project: Intro_JWT_5CHIF_2024
 * Created by: SF
 * Date: 07.11.2024
 * Time: 08:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {
  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private String role;    // only for testing!!!!
}
