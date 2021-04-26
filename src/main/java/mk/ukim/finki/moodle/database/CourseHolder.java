package mk.ukim.finki.moodle.database;

import lombok.Getter;
import mk.ukim.finki.moodle.model.Course;
import mk.ukim.finki.moodle.repository.CourseRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

@Component
@Getter
public class CourseHolder {

    public ArrayList<Course> courses;

    public final CourseRepository courseRepository;

    public CourseHolder(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    //@PostConstruct
    public void init() throws FileNotFoundException {

        courses = new ArrayList<>();
        File file = new File("src\\main\\java\\mk\\ukim\\finki\\moodle\\database\\courses.csv");
        if(!file.exists())
            throw  new FileNotFoundException("file");

        BufferedReader bf;
        try{
            bf = new BufferedReader(new FileReader(file));
            bf.lines().forEach(line -> {
                if(line.split(";").equals("false")){
                    return;
                }
                Course course = getCourseFromLine(line);

                if(course != null){
                    courses.add(course);
                }
            });

            this.courseRepository.saveAll(courses);

        }catch (FileNotFoundException exception){
            System.out.println("Error parsing file. Error: " + exception.getMessage());
        }

    }

    public Course getCourseFromLine(String line){
        String [] lineParts = line.split(";");

        String courseCode = lineParts[0];
        String courseName = lineParts[1];

        return new Course(
                courseCode, courseName
        );

    }
}