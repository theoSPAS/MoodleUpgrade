package mk.ukim.finki.moodle.database;

import lombok.Getter;
import mk.ukim.finki.moodle.model.Course;
import mk.ukim.finki.moodle.model.Professor;
import mk.ukim.finki.moodle.model.Project;
import mk.ukim.finki.moodle.model.exceptions.CourseNotFoundException;
import mk.ukim.finki.moodle.model.exceptions.ProfessorNotFoundException;
import mk.ukim.finki.moodle.repository.CourseRepository;
import mk.ukim.finki.moodle.repository.ProfessorRepository;
import mk.ukim.finki.moodle.repository.ProjectRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@Component
@Getter
public class ProjectHolder {

    public final ProjectRepository projectRepository;
    public final CourseRepository courseRepository;
    public final ProfessorRepository professorRepository;

    public ProjectHolder(ProjectRepository projectRepository, CourseRepository courseRepository, ProfessorRepository professorRepository) {
        this.projectRepository = projectRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    String line = "";

    //@PostConstruct
    public void saveProjectData(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\mk\\ukim\\finki\\moodle\\database\\projects.csv"));
            while((line = br.readLine()) != null){
                String [] data = line.split(";");
                Project project = new Project();

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(data[2]);

                project.setTitle(data[0]);
                project.setDescription(data[1]);
                project.setDeadline(date);

                Course course = this.courseRepository.findById(data[3]).orElseThrow(() -> new CourseNotFoundException(data[3]));

                Long id = Long.parseLong(data[4]);
                Professor professor = this.professorRepository.findById(id).orElseThrow(() -> new ProfessorNotFoundException(id));

                project.setCourse(course);
                project.setProfessor(professor);

                this.projectRepository.save(project);
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
