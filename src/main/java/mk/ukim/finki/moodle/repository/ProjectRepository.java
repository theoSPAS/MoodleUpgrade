package mk.ukim.finki.moodle.repository;
import mk.ukim.finki.moodle.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    Project findProjectByProfessorAndCourse(Professor professor, Course course);

    List<Project> findAllByProfessor(Professor professor);
}
