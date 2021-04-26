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
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique=true)
    String email;
    String name;
    String surname;

    @Column(length = 1000)
    String info;

    @ManyToMany(mappedBy = "professors")
    List<Student> students;

    @OneToMany(mappedBy = "professor")
    List<Project> projects;

    @ManyToMany
    List<Course> courses;

    public Professor(String name, String surname, String email, String info, List<Course> courses) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.courses = courses;
        this.info = info;
    }

    public Professor(Long id, String name, String surname,String email, String info) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.info = info;
    }

    public Professor(Long id, String name, String surname, String email) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getFullName() {
        return name + " " + surname;
    }

}
