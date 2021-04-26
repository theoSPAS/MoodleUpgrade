package mk.ukim.finki.moodle.web.controller;

import mk.ukim.finki.moodle.model.*;
import mk.ukim.finki.moodle.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final ProfessorService professorService;
    private final StudentService studentService;
    private final CourseService courseService;

    public StudentController(ProfessorService professorService, StudentService studentService, CourseService courseService) {
        this.professorService = professorService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public String getStudentsPage(Model model) {
        List<Student> students = studentService.listAll();
        model.addAttribute("students", students);
        model.addAttribute("bodyContent", "students");
        return "master-template";
    }

    @GetMapping("/info/{index}")
    public String getStudentPage(@PathVariable String index, Model model) {
        Student student = studentService.findByIndex(index);
        List<Course> courses = student.getCourses();
        List<Project> projects = student.getProjects();
        List<Professor> professors = student.getProfessors();
        model.addAttribute("student", student);
        model.addAttribute("courses", courses);
        model.addAttribute("projects", projects);
        model.addAttribute("professors", professors);

        model.addAttribute("bodyContent", "students");
        return "master-template";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddStudentPage(Model model) {
        List<Course> courses = this.courseService.findAll();
        List<Professor> professors = professorService.listAll();

        model.addAttribute("professors", professors);
        model.addAttribute("courses", courses);
        model.addAttribute("bodyContent", "add-student");

        return "master-template";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getEditStudentPage(@PathVariable String id, Model model) {
        Student student = this.studentService.findByIndex(id);
        List<Course> courses = courseService.findAll();
        List<Professor> professors = professorService.listAll();

        model.addAttribute("student", student);
        model.addAttribute("professors", professors);
        model.addAttribute("courses", courses);
        model.addAttribute("bodyContent", "add-student");

        return "master-template";

    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addStudent(@RequestParam String index,
                             @RequestParam String name,
                             @RequestParam String surname,
                             @RequestParam String email,
                             @RequestParam List<Course> courses,
                             @RequestParam List<Professor> professors) {
        this.studentService.save(index,name, surname, email, courses, professors);

        return "redirect:/students";

    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteStudent (@PathVariable String id) {
        this.studentService.deleteById(id);
        return "redirect:/students";
    }
}
