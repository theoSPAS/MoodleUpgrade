package mk.ukim.finki.moodle.web.controller;

import mk.ukim.finki.moodle.model.*;
import mk.ukim.finki.moodle.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/professors")
public class ProfessorController {


    private final ProfessorService professorService;
    private final CourseService courseService;

    public ProfessorController(ProfessorService professorService, CourseService courseService) {
        this.professorService = professorService;
        this.courseService = courseService;
    }

    @GetMapping
    public String getProfessorsPage(Model model) {
        List<Professor> professorList = professorService.listAll();
        model.addAttribute("professors", professorList);
        model.addAttribute("bodyContent", "professors");
        return "master-template";
    }

    @GetMapping("/info/{id}")
    public String getProfessorInfoPage(@PathVariable Long id, Model model) {
        Professor professor = professorService.findById(id);
        List<Course> courses = professor.getCourses();
        List<Project> projects = professor.getProjects();
        model.addAttribute("professor", professor);
        model.addAttribute("courses", courses);
        model.addAttribute("projects", projects);
        model.addAttribute("bodyContent", "professors");
        return "master-template";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddProfessorPage(Model model) {
        List<Course> courses = this.courseService.findAll();

        model.addAttribute("courses", courses);
        model.addAttribute("bodyContent", "add-professor");

        return "master-template";
    }


    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getEditProfessorPage(@PathVariable Long id, Model model) {
        Professor professor = this.professorService.findById(id);
        List<Course> courses = courseService.findAll();

        model.addAttribute("professor", professor);
        model.addAttribute("courses", courses);
        model.addAttribute("bodyContent", "add-professor");

        return "master-template";

    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addProfessor(@RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam String email,
                               @RequestParam List<Course> courses,
                               @RequestParam(required = false) String info) {
        this.professorService.save(name, surname, email, courses, info);

        return "redirect:/professors";

    }



    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProfessor(@PathVariable Long id) {
        this.professorService.deleteById(id);
        return "redirect:/professors";
    }
}
