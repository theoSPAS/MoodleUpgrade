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
public class Student {

    @Id
    String index;

    String name;
    String surname;

    @Column(unique=true)
    String email;

    @ManyToMany
    List<Project> projects;

    @ManyToMany
    List<Course> courses;

    @ManyToMany
    List<Professor> professors;


    public Student(String index, String name, String surname, String email) {
        this.index = index;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Student(String index, String name, String surname, String email, List<Course> courses, List<Professor> professors) {
        this.index = index;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.courses = courses;
        this.professors = professors;
    }

    public String getFullName() {
        return name + " " + surname;
    }
}
