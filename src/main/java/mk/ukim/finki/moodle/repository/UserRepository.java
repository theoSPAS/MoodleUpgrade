package mk.ukim.finki.moodle.repository;

import mk.ukim.finki.moodle.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsername(String s);

    Optional<User> findByUsernameAndPassword(String username, String password);
}