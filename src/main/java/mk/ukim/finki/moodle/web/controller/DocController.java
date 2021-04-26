package mk.ukim.finki.moodle.web.controller;

import mk.ukim.finki.moodle.model.*;
import mk.ukim.finki.moodle.model.enumerations.Grade;
import mk.ukim.finki.moodle.service.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/files")
public class DocController {

    private final DocService docService;
    private final StudentProjectsService studentProjectsService;
    private final ProjectService projectService;
    private final ProfessorService professorService;

    public DocController(DocService docService, StudentProjectsService studentProjectsService, ProjectService projectService, ProfessorService professorService) {
        this.docService = docService;
        this.studentProjectsService = studentProjectsService;
        this.projectService = projectService;
        this.professorService = professorService;
    }

    @PostMapping("/{idProject}")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile file,
                                      @PathVariable Long idProject,
                                      Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        StudentProjects studentProjects = studentProjectsService.findByStudentAndProject(user.getUsername(), idProject);
        Document document = this.docService.saveFile(file);
        studentProjects.setIdDocument(document.getId());

        studentProjectsService.save(studentProjects);


        return "redirect:/projects/user";
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long id) {
        Document doc = this.docService.getFile(id).get();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getDocType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment:filename=\"" + doc.getDocName() + "\"")
                .body(new ByteArrayResource(doc.getData()));
    }

    @GetMapping("/all")
    public String getAllUploadsPage(Model model, Authentication authentication) {
        List<Document> docs = this.docService.getFiles();
        List<StudentProjects> studentProjects = studentProjectsService.listAll();

        User user = (User) authentication.getPrincipal();
        Professor professor = professorService.findByEmail(user.getUsername());

        List<Project> profProjects = projectService.uploadedProjectsForProfessor(professor,studentProjects);

        model.addAttribute("projects", profProjects);
        model.addAttribute("grades", Grade.values());
        model.addAttribute("docs", docs);
        model.addAttribute("studentProjects", studentProjects);
        model.addAttribute("bodyContent", "all-uploads");

        return "master-template";
    }

    @PostMapping("/grade/{id}")
    public String gradeProject(@PathVariable Long id,
                               @RequestParam Grade grade) {
        StudentProjects studentProjects = studentProjectsService.findByDocument(id);

        this.studentProjectsService.gradeProject(studentProjects, grade);

        return "redirect:/files/all";
    }
}
