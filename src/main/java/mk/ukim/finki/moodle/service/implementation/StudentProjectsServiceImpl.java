package mk.ukim.finki.moodle.service.implementation;

import mk.ukim.finki.moodle.model.Project;
import mk.ukim.finki.moodle.model.Student;
import mk.ukim.finki.moodle.model.StudentProjects;
import mk.ukim.finki.moodle.model.enumerations.Grade;
import mk.ukim.finki.moodle.model.exceptions.StudentProjectNotFoundException;
import mk.ukim.finki.moodle.repository.StudentProjectsRepository;
import mk.ukim.finki.moodle.service.StudentProjectsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentProjectsServiceImpl implements StudentProjectsService {

    private final StudentProjectsRepository studentProjectsRepository;

    public StudentProjectsServiceImpl(StudentProjectsRepository studentProjectsRepository) {
        this.studentProjectsRepository = studentProjectsRepository;
    }

    @Override
    public List<StudentProjects> listAll() {
        return studentProjectsRepository.findAll();
    }

    @Override
    public StudentProjects findByStudentAndProject(String studentId, Long projectId) {
        return this.studentProjectsRepository.findStudentProjectsByIdStudentAndIdProject(studentId, projectId)
                .orElseThrow(() -> new StudentProjectNotFoundException(studentId, projectId));
    }

    @Override
    public List<StudentProjects> findByStudent(String idStudent) {
        return this.studentProjectsRepository.findAllByIdStudent(idStudent);
    }

    @Override
    public StudentProjects gradeProject(StudentProjects studentProjects, Grade grade) {
        studentProjects.setGrade(grade);

        return this.studentProjectsRepository.save(studentProjects);
    }

    @Override
    public StudentProjects findByDocument(Long idDocument) {
        return studentProjectsRepository.findStudentProjectsByIdDocument(idDocument)
                .orElseThrow(() -> new StudentProjectNotFoundException(idDocument));
    }

    @Override
    public StudentProjects save(StudentProjects studentProjects) {
        return this.studentProjectsRepository.save(studentProjects);
    }

    @Override
    public StudentProjects add(Student student, Project project) {
        StudentProjects studentProjects = new StudentProjects(student.getIndex(),project.getId());
        return studentProjectsRepository.save(studentProjects);
    }
}
