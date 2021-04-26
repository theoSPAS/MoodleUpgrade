package mk.ukim.finki.moodle.model.exceptions;

public class EmailDoesNotExistException extends RuntimeException {
    public EmailDoesNotExistException() {
        super(String.format("This email does not exist in the database!"));
    }
}
