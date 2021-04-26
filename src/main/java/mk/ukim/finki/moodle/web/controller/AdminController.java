package mk.ukim.finki.moodle.web.controller;

import mk.ukim.finki.moodle.model.Course;
import mk.ukim.finki.moodle.model.Professor;
import mk.ukim.finki.moodle.model.Student;
import mk.ukim.finki.moodle.service.CourseService;
import mk.ukim.finki.moodle.service.ProfessorService;
import mk.ukim.finki.moodle.service.ProjectService;
import mk.ukim.finki.moodle.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CourseService courseService;
    private final ProfessorService professorService;
    private final StudentService studentService;
    private final ProjectService projectService;

    public AdminController(CourseService courseService, ProfessorService professorService,StudentService studentService, ProjectService projectService) {
        this.courseService = courseService;
        this.professorService = professorService;
        this.studentService = studentService;
        this.projectService = projectService;
    }

    @GetMapping
    public String getAdminPage (@RequestParam(required = false) String error, Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
            model.addAttribute("bodyContent","error");
            return "master-template";
        }

        model.addAttribute("bodyContent", "admin");
        return "master-template";
    }

    @GetMapping ("/professors-to-course")
    public String getAddProfessorsToCourse(@RequestParam(required = false) String error,Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
            return "error";
        }
        List<Course> courses = this.courseService.findAll();
        List<Professor> professors = this.professorService.listAll();

        model.addAttribute("courses", courses);
        model.addAttribute("professors", professors);
        model.addAttribute("bodyContent", "professors-to-course");

        return "master-template";
    }

    @PostMapping("/professors-to-course")
    public String addProfessorToCourse(@RequestParam List<Long> professors,
                                       @RequestParam String courseId) {
        List<Professor> professorList = this.professorService.findAllById(professors);
        Course course = this.courseService.findById(courseId);

        this.professorService.addCourses(professorList, course);

        return "redirect:/admin";
    }

    @GetMapping("/students-to-professor")
    public String getAddStudentToProfessor(Model model) {
        List<Student> students = this.studentService.listAll();
        List<Professor> professors = this.professorService.listAll();

        model.addAttribute("students", students);
        model.addAttribute("professors", professors);
        model.addAttribute("bodyContent", "students-to-professor");

        return "master-template";
    }


    @PostMapping("/students-to-professor")
    public String AddStudentsToProfessor(@RequestParam Long professorId,
                                         @RequestParam List<String> students){
        List<Student> studentList = studentService.findAllById(students);
        Professor professor = professorService.findById(professorId);

        this.studentService.addProfessor(studentList,professor);

        return "redirect:/admin";
    }

    @GetMapping("/students-to-course")
    public String getAddStudentsToCourse(Model model, @RequestParam(required = false) String error){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", "error");
            model.addAttribute("bodyContent","error");
            return "master-template";
        }
        List<Student> students = this.studentService.listAll();
        List<Course> courses = this.courseService.findAll();

        model.addAttribute("students", students);
        model.addAttribute("courses", courses);
        model.addAttribute("bodyContent", "students-to-course");

        return "master-template";
    }

    @PostMapping("/students-to-course")
    public String AddStudentToCourse(@RequestParam String courseId,
                                     @RequestParam List<String> students){
        List<Student> studentsList = this.studentService.findAllById(students);
        Course course = this.courseService.findById(courseId);

        this.studentService.addCourses(studentsList,course);

        return "redirect:/admin";
    }
}
