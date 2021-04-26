package mk.ukim.finki.moodle.web.controller;

import mk.ukim.finki.moodle.model.*;
import mk.ukim.finki.moodle.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String getCoursePage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Course> courses = this.courseService.findAll();

        model.addAttribute("courses", courses);
        model.addAttribute("bodyContent", "courses");
        return "master-template";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddCoursePage(Model model) {
        model.addAttribute("bodyContent", "add-course");
        return "master-template";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getEditCoursePage(@PathVariable String id, Model model) {
        Course course = this.courseService.findById(id);

        model.addAttribute("course",course);
        model.addAttribute("bodyContent", "add-course");
        return "master-template";

    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addCourse(@RequestParam String name,
                            @RequestParam String code) {
        this.courseService.save(code, name);
        return "redirect:/courses";
    }

    @GetMapping("/delete/{code}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCourse(@PathVariable String code) {
        this.courseService.deleteByCode(code);
        return "redirect:/courses";
    }
}
