package mk.ukim.finki.moodle.model.exceptions;

import mk.ukim.finki.moodle.model.Course;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentCourseAlreadyExistException extends RuntimeException {
    public StudentCourseAlreadyExistException(String index, Course course) {
        super(String.format("Student with %s id and course %s already exist",index, course.getCode()));
    }
}
