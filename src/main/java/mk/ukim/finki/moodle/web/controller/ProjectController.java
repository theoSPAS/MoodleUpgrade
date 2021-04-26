package mk.ukim.finki.moodle.web.controller;

import mk.ukim.finki.moodle.model.*;;
import mk.ukim.finki.moodle.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final ProfessorService professorService;
    private final StudentService studentService;
    private final ProjectService projectService;
    private final CourseService courseService;
    private final StudentProjectsService studentProjectsService;
    private final DocService docService;

    public ProjectController(ProfessorService professorService, StudentService studentService, ProjectService projectService, CourseService courseService, StudentProjectsService studentProjectsService, DocService docService) {
        this.professorService = professorService;
        this.studentService = studentService;
        this.projectService = projectService;
        this.courseService = courseService;
        this.studentProjectsService = studentProjectsService;
        this.docService = docService;
    }

    @GetMapping
    public String getProjectsPage(Authentication authentication, Model model) {
        List<Project> allProjects = projectService.listAll();

        if (authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_STUDENT"))) {

            User user = (User) authentication.getPrincipal();

            Student student = studentService.findByIndex(user.getUsername());

            List<Project> projectsForStudents = this.projectService.projectsForStudents(allProjects,student);


            model.addAttribute("projects", projectsForStudents);

            if (projectsForStudents.isEmpty()) {
                model.addAttribute("hasError", true);
                model.addAttribute("error", "There are no current projects for your courses");
            }

        }
        else if (authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_PROFESSOR"))) {
            User user = (User) authentication.getPrincipal();

            Professor professor = this.professorService.findByEmail(user.getUsername());

            List<Project> projectsForProfessors =  this.projectService.projectsForProfessors(allProjects, professor);

            model.addAttribute("projects", projectsForProfessors);

            if (projectsForProfessors.isEmpty()) {
                model.addAttribute("hasError", true);
                model.addAttribute("error", "There are no current projects for your courses");
            }
        } else {
            model.addAttribute("projects", allProjects);
        }
        model.addAttribute("bodyContent", "projects");
        return "master-template";
    }

    @PostMapping("/{id}")
    public String getSelectProjectPage(@PathVariable Long id, Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        Student student = studentService.findByIndex(user.getUsername());
        Project project = projectService.findById(id);
        this.studentProjectsService.add(student, project);

        return "redirect:/projects/user";
    }

    @GetMapping("/info/{id}")
    public String getProjectInfoPage(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        model.addAttribute("bodyContent", "projects");
        return "master-template";
    }

    @GetMapping("/user")
    public String getMyProjectsPage(@RequestParam(required = false) String error,
                                    Model model,
                                    Authentication authentication) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }


        User user = (User) authentication.getPrincipal();
        Student student = studentService.findByIndex(user.getUsername());

        List<StudentProjects> myProjects = studentProjectsService.findByStudent(student.getIndex());
        List<Project> allProjects = projectService.listAll();
        List<Document> docs = this.docService.getFiles();

        model.addAttribute("myProjects", myProjects);
        model.addAttribute("allProjects", allProjects);
        model.addAttribute("student", student);
        model.addAttribute("docs", docs);
        model.addAttribute("bodyContent", "my-projects");
        return "master-template";
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String getAddProjectPage(Model model) {
        List<Course> courses = this.courseService.findAll();
        List<Professor> professors = this.professorService.listAll();

        model.addAttribute("courses", courses);
        model.addAttribute("professors", professors);
        model.addAttribute("bodyContent", "add-project");

        return "master-template";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
    public String getEditProjectPage(@PathVariable Long id, Model model) {
        Project project = this.projectService.findById(id);
        List<Course> courses = courseService.findAll();
        List<Professor> professors = professorService.listAll();

        model.addAttribute("project",project);
        model.addAttribute("professors", professors);
        model.addAttribute("courses", courses);
        model.addAttribute("bodyContent", "add-professor");

        return "master-template";

    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String addProject(@RequestParam String title,
                             @RequestParam String description,
                             @RequestParam Date deadline,
                             @RequestParam String courseId,
                             @RequestParam Long professorId) {
        this.projectService.save(title, description, deadline, courseId, professorId);

        return "redirect:/projects";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_PROFESSOR')")
    public String deleteProject (@PathVariable Long id) {
        this.projectService.deleteById(id);
        return "redirect:/projects";
    }
}
