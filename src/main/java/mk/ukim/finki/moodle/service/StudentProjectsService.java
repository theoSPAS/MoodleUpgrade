package mk.ukim.finki.moodle.service;

import mk.ukim.finki.moodle.model.Project;
import mk.ukim.finki.moodle.model.Student;
import mk.ukim.finki.moodle.model.StudentProjects;
import mk.ukim.finki.moodle.model.enumerations.Grade;

import java.util.List;

public interface StudentProjectsService {

    List<StudentProjects> listAll();

    StudentProjects findByStudentAndProject(String studentId, Long projectId);

    List<StudentProjects> findByStudent(String idStudent);

    StudentProjects gradeProject(StudentProjects studentProjects, Grade grade);

    StudentProjects findByDocument(Long idDocument);

    StudentProjects save(StudentProjects studentProjects);

    StudentProjects add(Student student, Project project);
}
