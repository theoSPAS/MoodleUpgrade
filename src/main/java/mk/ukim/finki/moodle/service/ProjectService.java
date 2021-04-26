package mk.ukim.finki.moodle.service;

import mk.ukim.finki.moodle.model.*;

import java.util.Date;
import java.util.List;

public interface ProjectService {
    Project findById(Long id);

    List<Project> findByProfessorAndCourses(Professor professor, List<Course> courses);

    List<Project> listAll();

    Project save(String title, String description, Date deadline, String courseId, Long professorId);

    List<Project> uploadedProjectsForProfessor(Professor professor,List<StudentProjects> studentProjects);

    void deleteById(Long id);

    List<Project> projectsForProfessors(List<Project> allProjects, Professor professor);

    List<Project> projectsForStudents(List<Project> allProjects, Student student);
}