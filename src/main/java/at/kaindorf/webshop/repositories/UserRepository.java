package at.kaindorf.webshop.repositories;

import at.kaindorf.webshop.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Project: Intro_JWT_5CHIF_2024
 * Created by: SF
 * Date: 07.11.2024
 * Time: 08:39
 */
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String username);
}
