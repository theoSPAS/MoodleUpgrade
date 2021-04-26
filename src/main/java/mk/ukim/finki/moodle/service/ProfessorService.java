package mk.ukim.finki.moodle.service;

import mk.ukim.finki.moodle.model.*;

import java.util.List;

public interface ProfessorService {

    Professor findById(Long id);

    List<Professor> listAll();

    Professor save(String name, String surname, String email, List<Course> courses, String info);

    Professor findByEmail(String username);

    void deleteById(Long id);

    void addCourses(List<Professor> professors, Course course);

    List<Professor> findAllById(List<Long> professors);
}
