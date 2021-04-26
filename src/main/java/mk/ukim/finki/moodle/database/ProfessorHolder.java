package mk.ukim.finki.moodle.database;

import lombok.Getter;
import mk.ukim.finki.moodle.model.Course;
import mk.ukim.finki.moodle.model.Professor;
import mk.ukim.finki.moodle.repository.CourseRepository;
import mk.ukim.finki.moodle.repository.ProfessorRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Getter
public class ProfessorHolder {

    public ArrayList<Professor> professors;

    public final ProfessorRepository professorRepository;
    public final CourseRepository courseRepository;


    public ProfessorHolder(ProfessorRepository professorRepository, CourseRepository courseRepository) {
        this.professorRepository = professorRepository;
        this.courseRepository = courseRepository;
    }

   // @PostConstruct
    public void init() throws FileNotFoundException {

        professors = new ArrayList<>();
        File file = new File("src\\main\\java\\mk\\ukim\\finki\\moodle\\database\\professorsInfo.csv");
        if (!file.exists())
            throw new FileNotFoundException("file");

        BufferedReader bf;
        try {
            bf = new BufferedReader(new FileReader(file));
            bf.lines().forEach(line -> {
                if (line.split(";").equals("false")) {
                    return;
                }
                Professor professor = getProfessorFromLine(line);

                if (professor != null) {
                    professors.add(professor);
                }
            });

            this.professorRepository.saveAll(professors);

        } catch (FileNotFoundException exception) {
            System.out.println("Error parsing file. Error: " + exception.getMessage());
        }

    }


    public Professor getProfessorFromLine(String line) {
        String[] lineParts = line.split(";");

        String professorId = lineParts[0];
        String professorFirstName = lineParts[1];
        String professorLastName = lineParts[2];
        String professorEmail = lineParts[3];
        String info = lineParts[4];

        Professor professor = new Professor(
                Long.parseLong(professorId),
                professorFirstName,
                professorLastName,
                professorEmail,
                info
        );

        if (lineParts.length > 5) {

            for (int i = 5; i < lineParts.length; i++) {
                String tmp = lineParts[i];
                List<Course> courses = courseRepository.findAllById(Arrays.asList(tmp));
                professor.setCourses(courses);

            }

        }

        return professor;

    }
}