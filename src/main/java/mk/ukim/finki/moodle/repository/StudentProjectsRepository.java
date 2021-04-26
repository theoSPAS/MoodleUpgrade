package mk.ukim.finki.moodle.repository;
import mk.ukim.finki.moodle.model.StudentProjects;
import mk.ukim.finki.moodle.model.StudentProjectsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentProjectsRepository extends JpaRepository<StudentProjects, StudentProjectsId> {

    Optional<StudentProjects> findStudentProjectsByIdStudentAndIdProject (String idStudent, Long idProject);

    List<StudentProjects> findAllByIdStudent(String idStudent);

    Optional<StudentProjects> findStudentProjectsByIdDocument(Long idDocument);
}
