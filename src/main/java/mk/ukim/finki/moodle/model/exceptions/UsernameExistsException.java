package mk.ukim.finki.moodle.model.exceptions;

public class UsernameExistsException extends RuntimeException{
    public UsernameExistsException(String username) {
        super(String.format("User with this %d username exists",username));
    }
}
