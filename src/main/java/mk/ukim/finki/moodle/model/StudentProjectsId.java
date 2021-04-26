package mk.ukim.finki.moodle.model;


import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class StudentProjectsId implements Serializable {

    private String idStudent;
    private Long idProject;

    @Id
    @Column(name = "students_index")
    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    @Id
    @Column(name="projects_id")
    public Long getIdProject() {
        return idProject;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }


}
