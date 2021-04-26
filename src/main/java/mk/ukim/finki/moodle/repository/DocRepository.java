package mk.ukim.finki.moodle.repository;

import mk.ukim.finki.moodle.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocRepository extends JpaRepository<Document,Long> {
}
