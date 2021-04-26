package mk.ukim.finki.moodle.service.implementation;

import mk.ukim.finki.moodle.model.*;
import mk.ukim.finki.moodle.model.exceptions.*;
import mk.ukim.finki.moodle.repository.*;
import mk.ukim.finki.moodle.service.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ProjectRepository projectRepository;

    public StudentServiceImpl(StudentRepository studentRepository, ProjectRepository projectRepository) {
        this.studentRepository = studentRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    public Student findByIndex(String username) {
        return studentRepository.findById(username).orElseThrow(() -> new StudentNotFoundException(username));
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }


    @Override
    public List<Student> findAllById(List<String> studentsIds) {
        return studentRepository.findAllById(studentsIds);
    }

    @Override
    public Student save(String index, String name, String surname, String email, List<Course> courses, List<Professor> professors) {
        Student student = new Student(index, name, surname, email, courses, professors);
        return this.studentRepository.save(student);
    }

    @Override
    public void deleteById(String id) {
        this.studentRepository.deleteById(id);
    }

    @Override
    public void addCourses(List<Student> students, Course course) {
        for (Student student : students){
            List<Course> courses = student.getCourses();
            if (courses.contains(course)){
                throw new StudentCourseAlreadyExistException(student.getIndex(), course);
            }
            courses.add(course);
            student.setCourses(courses);

            studentRepository.save(student);
        }
    }

    @Override
    public void addProfessor(List<Student> studentList, Professor professor) {
        for (Student student : studentList){
            List<Professor> professors = student.getProfessors();
            if (professors.contains(professor)){
                throw new StudentProfessorAlreadyExistsException(student.getIndex(), professor);
            }
            professors.add(professor);
            student.setProfessors(professors);
            studentRepository.save(student);
        }
    }
}
