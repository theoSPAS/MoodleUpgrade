package mk.ukim.finki.moodle.service;

import mk.ukim.finki.moodle.model.*;

import java.util.List;

public interface StudentService {

    Student findByIndex(String username);

    List<Student> listAll();


    List<Student> findAllById(List<String> studentsIds);

    Student save(String index, String name, String surname, String email, List<Course> courses, List<Professor> professors);

    void deleteById(String id);

    void addCourses(List<Student> students, Course course);

    void addProfessor(List<Student> studentList, Professor professor);
}
