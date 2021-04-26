package mk.ukim.finki.moodle.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "third")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    @Column (length = 1000)
    String description;
    Date deadline;

    @ManyToOne
    Course course;

    @ManyToOne
    Professor professor;

    @ManyToMany(mappedBy = "projects")
    List<Student> students;



    public Project(String title, String description, Date deadline, Course course) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.course = course;
    }

    public Project(String title, String description, Date deadline, Course course, Professor professor) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.course = course;
        this.professor = professor;
    }

    @Override
    public String toString() {
        return "";
    }

}
