package mk.ukim.finki.moodle.database;

import lombok.Getter;
import mk.ukim.finki.moodle.model.Course;
import mk.ukim.finki.moodle.model.Professor;
import mk.ukim.finki.moodle.model.Student;
import mk.ukim.finki.moodle.repository.CourseRepository;
import mk.ukim.finki.moodle.repository.ProfessorRepository;
import mk.ukim.finki.moodle.repository.StudentRepository;
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
public class StudentHolder {

    public ArrayList<Student> students;

    public final StudentRepository studentRepository;
    public final ProfessorRepository professorRepository;
    public final CourseRepository courseRepository;


    public StudentHolder(StudentRepository studentRepository, ProfessorRepository professorRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.professorRepository = professorRepository;
        this.courseRepository = courseRepository;
    }

   // @PostConstruct
    public void init() throws FileNotFoundException {

        students = new ArrayList<>();
        File file = new File("src\\main\\java\\mk\\ukim\\finki\\moodle\\database\\students.csv");
        if(!file.exists())
            throw  new FileNotFoundException("file");

        BufferedReader bf;
        try{
            bf = new BufferedReader(new FileReader(file));
            bf.lines().forEach(line -> {
                if(line.split(";").equals("false")){
                    return;
                }
                Student student = getStudentFromLine(line);

                if(student != null){
                    students.add(student);
                }
            });

            this.studentRepository.saveAll(students);

        }catch (FileNotFoundException exception){
            System.out.println("Error parsing file. Error: " + exception.getMessage());
        }

    }

    public Student getStudentFromLine(String line){
        String [] lineParts = line.split(";");

        String index = lineParts[0];
        String studentFirstName = lineParts[1];
        String studentLastName = lineParts[2];
        String studentEmail = lineParts[3];
        if (lineParts.length > 4) {
            String p = lineParts[4];
            String[] pLine = p.split(",");

            String c = lineParts[5];
            String[] cLine = c.split(",");

            Long[] pLine2 = new Long[10];
            for (int i=0; i< pLine.length; i++){
                pLine2[i] = Long.parseLong(pLine[i]);
            }
            List<Course> courses = courseRepository.findAllById(Arrays.asList(cLine));
            List<Professor> professors = professorRepository.findAllById(Arrays.asList(pLine2));

            return new Student(
                    index,
                    studentFirstName,
                    studentLastName,
                    studentEmail,
                    courses,
                    professors
            );
        } else {
            return new Student(
                    index,
                    studentFirstName,
                    studentLastName,
                    studentEmail
            );

        }

    }
}
