package mk.ukim.finki.moodle.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "third")
public class Course {

    @Id
    String code;

    String name;

    @ManyToMany (mappedBy = "courses")
    List<Professor> professors;

    @ManyToMany (mappedBy = "courses")
    List<Student> students;

    @OneToMany (mappedBy = "course")
    List<Project> project;

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
