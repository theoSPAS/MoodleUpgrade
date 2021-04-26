package mk.ukim.finki.moodle.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class ProjectAlreadyChoosenByStudent extends RuntimeException {
    public ProjectAlreadyChoosenByStudent(Long projectId, String index) {
        super(String.format("Project with id: %d already choosen by student with this index %s",projectId,index));
    }
}
