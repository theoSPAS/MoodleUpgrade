package mk.ukim.finki.moodle.model.exceptions;

public class StudentProjectNotFoundException extends RuntimeException {
    public StudentProjectNotFoundException(String idStudent, Long idProject) {
        super(String.format("Student with index: %s doesn't have the project with id: %d",idStudent, idProject));
    }

    public StudentProjectNotFoundException(Long docId) {
        super(String.format("This project doesn't have the uploaded file with index: %d ",docId));

    }
}
