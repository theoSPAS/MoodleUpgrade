package mk.ukim.finki.moodle.service.implementation;

import mk.ukim.finki.moodle.model.*;
import mk.ukim.finki.moodle.model.exceptions.*;
import mk.ukim.finki.moodle.repository.*;
import mk.ukim.finki.moodle.service.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(String code) {
        return courseRepository.findById(code).orElseThrow(() -> new CourseNotFoundException(code));
    }

    @Override
    public Course save(String name, String code) {
        Course course = new Course(name,code);

        return this.courseRepository.save(course);
    }

    @Override
    public Course addStudents(List<Student> studentList, Course course) {
        List<Student> existingStudents = course.getStudents();
        existingStudents.addAll(studentList);

        course.setStudents(existingStudents);

        return this.courseRepository.save(course);
    }

    @Override
    public Course addProfessors(List<Professor> professorList, Course course) {
        List<Professor> existingProfessors = course.getProfessors();
        existingProfessors.addAll(professorList);

        course.setProfessors(existingProfessors);

        return this.courseRepository.save(course);
    }

    @Override
    public void deleteByCode(String code) {
        this.courseRepository.deleteById(code);
    }

    @Override
    public List<Course> findAllByCode(List<String> courses) {
        return courseRepository.findAllById(courses);
    }
}
