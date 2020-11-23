package serwisAudio.repository;

import serwisAudio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository         // repozytorium - interfejs implementujący zapytania SQL
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);      // SELECT * FROM user WHERE email = ?;
}
