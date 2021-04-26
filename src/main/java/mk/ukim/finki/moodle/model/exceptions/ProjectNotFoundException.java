package mk.ukim.finki.moodle.model.exceptions;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(Long projectId) {
        super(String.format("Project not found with %d id",projectId) );
    }
}
