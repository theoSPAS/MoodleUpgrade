package mk.ukim.finki.moodle.model.exceptions;

import mk.ukim.finki.moodle.model.Professor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CourseProfessorAlreadyExistsException extends RuntimeException {
    public CourseProfessorAlreadyExistsException(String code, Professor professor) {
        super(String.format("Professor with %s id and course %s already exist",professor.getId(),code));
    }
}
