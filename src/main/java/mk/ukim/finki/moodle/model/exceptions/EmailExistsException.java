package mk.ukim.finki.moodle.model.exceptions;

public class EmailExistsException extends Throwable {
    public EmailExistsException(String email) {
        super(String.format("User with this %d email exists",email));
    }
}
