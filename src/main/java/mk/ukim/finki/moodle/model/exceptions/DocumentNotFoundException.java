package mk.ukim.finki.moodle.model.exceptions;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(Long id) {
        super(String.format("Document with id: %d was not found.",id));
    }
}
