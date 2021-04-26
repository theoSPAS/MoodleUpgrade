package mk.ukim.finki.moodle.model.exceptions;

import mk.ukim.finki.moodle.model.Professor;

public class StudentProfessorAlreadyExistsException extends RuntimeException {
    public StudentProfessorAlreadyExistsException(String index, Professor professor) {
        super(String.format("Student with %s id and professor %s already exist",index, professor.getId()));
    }
}
