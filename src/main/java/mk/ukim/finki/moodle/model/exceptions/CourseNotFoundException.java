package mk.ukim.finki.moodle.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends RuntimeException{
    public CourseNotFoundException(String courseId) {
        super(String.format("Course with this %d code not found",courseId));
    }
}
