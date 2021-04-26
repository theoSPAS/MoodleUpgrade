package mk.ukim.finki.moodle.repository;

import mk.ukim.finki.moodle.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,String> {

    Optional<Student> findByEmail(String email);


}
