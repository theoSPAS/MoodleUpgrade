package mk.ukim.finki.moodle.service.implementation;

import mk.ukim.finki.moodle.model.*;
import mk.ukim.finki.moodle.model.exceptions.CourseNotFoundException;
import mk.ukim.finki.moodle.model.exceptions.ProfessorNotFoundException;
import mk.ukim.finki.moodle.model.exceptions.ProjectNotFoundException;
import mk.ukim.finki.moodle.model.exceptions.StudentNotFoundException;
import mk.ukim.finki.moodle.repository.CourseRepository;
import mk.ukim.finki.moodle.repository.ProfessorRepository;
import mk.ukim.finki.moodle.repository.ProjectRepository;
import mk.ukim.finki.moodle.repository.StudentRepository;
import mk.ukim.finki.moodle.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ProjectServiceImpl implements ProjectService {

   private final ProjectRepository projectRepository;
   private final ProfessorRepository professorRepository;
   private final CourseRepository courseRepository;
   private final StudentRepository studentRepository;

   public ProjectServiceImpl(ProjectRepository projectRepository, ProfessorRepository professorRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
      this.projectRepository = projectRepository;
      this.professorRepository = professorRepository;
      this.courseRepository = courseRepository;
      this.studentRepository = studentRepository;
   }

   @Override
   public Project findById(Long id) {
      return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
   }

   @Override
   public List<Project> findByProfessorAndCourses(Professor professor, List<Course> courses) {
      List<Project> projects = new ArrayList<>();
      for (Course course : courses) {
         Project project = projectRepository.findProjectByProfessorAndCourse(professor, course);
         projects.add(project);
      }
      return projects;
   }

   @Override
   public List<Project> listAll() {
      return projectRepository.findAll();
   }

   @Override
   public Project save(String title, String description, Date deadline, String courseId, Long professorId) {
      Professor professor = this.professorRepository.findById(professorId)
              .orElseThrow(() -> new ProfessorNotFoundException(professorId));

      Course course = this.courseRepository.findById(courseId)
              .orElseThrow(() -> new CourseNotFoundException(courseId));

      Project project = new Project(title, description, deadline, course, professor);

      return projectRepository.save(project);
   }

   @Override
   public List<Project> uploadedProjectsForProfessor(Professor professor, List<StudentProjects> studentProjects) {

      List<Project> returnList = new ArrayList<>();
      for (StudentProjects sp : studentProjects){
         Student student = studentRepository.findById(sp.getIdStudent())
                 .orElseThrow(()->new StudentNotFoundException(sp.getIdStudent()));
         List<Professor> professorList = student.getProfessors();

         for (Professor p : professorList){
            if (p == professor){
               returnList = projectRepository.findAllByProfessor(professor);
            }
         }
      }
      return returnList;

   }

   @Override
   public void deleteById(Long id) {
      this.projectRepository.deleteById(id);
   }

   @Override
   public List<Project> projectsForProfessors(List<Project> allProjects, Professor professor) {
      List<Project> projectsForProfessors = new ArrayList<>();

      for (Project project : allProjects) {
         for (Course course : professor.getCourses()) {
            if (project.getCourse() == course && project.getProfessor() == professor) {
               projectsForProfessors.add(project);
            }

         }
      }

      return projectsForProfessors;
   }


   @Override
   public List<Project> projectsForStudents(List<Project> allProjects, Student student) {
      List<Project> projectsForStudents = new ArrayList<>();

      for (Project project : allProjects) {
         for (Course course : student.getCourses()) {
            for (Professor professor : student.getProfessors()) {
               if (project.getCourse() == course && project.getProfessor() == professor) {
                  projectsForStudents.add(project);
               }
            }
         }
      }

      return projectsForStudents;
   }
}

