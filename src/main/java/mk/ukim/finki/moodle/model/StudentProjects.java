package mk.ukim.finki.moodle.model;


import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.moodle.model.enumerations.Grade;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "student_projects", schema = "third")
@IdClass(StudentProjectsId.class)
public class StudentProjects {

    private String idStudent;

    private Long idProject;

    @Enumerated(value = EnumType.STRING)
    private Grade grade;

    private Long idDocument;

    @Id
    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    @Id
    public Long getIdProject() {
        return idProject;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }

    @Basic
    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Basic
    public Long getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(Long idDocument) {
        this.idDocument = idDocument;
    }

    public StudentProjects(String idStudent, Long idProject) {
        this.idStudent = idStudent;
        this.idProject = idProject;
    }

    public StudentProjects() {
    }
}
