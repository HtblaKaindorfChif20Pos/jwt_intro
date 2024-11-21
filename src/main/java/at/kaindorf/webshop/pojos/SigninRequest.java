package at.kaindorf.webshop.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Project: Intro_JWT_5CHIF_2024
 * Created by: SF
 * Date: 07.11.2024
 * Time: 08:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SigninRequest {
  private String username;
  private String password;
}
