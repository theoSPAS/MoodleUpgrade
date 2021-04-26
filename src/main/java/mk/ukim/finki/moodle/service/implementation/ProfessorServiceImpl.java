package mk.ukim.finki.moodle.service.implementation;

import mk.ukim.finki.moodle.model.*;
import mk.ukim.finki.moodle.model.exceptions.*;
import mk.ukim.finki.moodle.repository.*;
import mk.ukim.finki.moodle.service.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorServiceImpl(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @Override
    public Professor findById(Long id) {
        return professorRepository.findById(id).orElseThrow(() -> new ProfessorNotFoundException(id));
    }

    @Override
    public List<Professor> listAll() {
        return professorRepository.findAll();
    }

    @Override
    public Professor save(String name, String surname, String email, List<Course> courses, String info) {
        Professor professor = new Professor(name, surname, email, info, courses);
        this.professorRepository.save(professor);

        return professor;
    }

    @Override
    public Professor findByEmail(String email) {
        return professorRepository.findByEmail(email)
                .orElseThrow(()->new ProfessorNotFoundException(email));

    }

    @Override
    public void deleteById(Long id) {
        this.professorRepository.deleteById(id);
    }

    @Override
    public void addCourses(List<Professor> professors, Course course) {
        for (Professor professor : professors){
            List<Course> courses = professor.getCourses();
            if (courses.contains(course)){
                throw new CourseProfessorAlreadyExistsException(course.getCode(), professor);
            }
            courses.add(course);
            professor.setCourses(courses);

            professorRepository.save(professor);
        }
    }

    @Override
    public List<Professor> findAllById(List<Long> professors) {
        return professorRepository.findAllById(professors);
    }
}
