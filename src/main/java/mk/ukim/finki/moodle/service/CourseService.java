package mk.ukim.finki.moodle.service;

import mk.ukim.finki.moodle.model.*;

import java.util.List;

public interface CourseService {

    List<Course> findAll();

    Course findById(String code);

    Course save(String name, String code);

    Course addStudents(List<Student> studentList, Course course);

    Course addProfessors(List<Professor> professorList, Course course);

    void deleteByCode(String code);

    List<Course> findAllByCode(List<String> courses);
}
