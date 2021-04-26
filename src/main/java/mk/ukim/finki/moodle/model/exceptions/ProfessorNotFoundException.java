package mk.ukim.finki.moodle.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProfessorNotFoundException extends RuntimeException{

    public ProfessorNotFoundException(Long id){
        super(String.format("Professor  with id: %d was not found",id));
    }

    public ProfessorNotFoundException(String email){
        super(String.format("Professor  with email: %s was not found",email));
    }

}
