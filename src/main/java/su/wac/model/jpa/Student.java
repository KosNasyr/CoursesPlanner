package su.wac.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;

@Entity
@Table(name = "student")
@Getter
@Setter
@ToString
public class Student  extends CoursesObject {

    public Student() {
    }

    public Student(String fullName, String eMail, Long telNumber, String password,String role) {
        this.fullName = fullName;
        this.eMail = eMail;
        this.telNumber = telNumber;
        this.password = password;
        this.role = role;
    }

    public Student(String fullName, String eMail, Long telNumber) {
        this.fullName = fullName;
        this.eMail = eMail;
        this.telNumber = telNumber;
    }

    public Student(Long id, String fullName, String eMail,Long telNumber, String password,String role) {
        super(id);
        this.fullName = fullName;
        this.eMail = eMail;
        this.telNumber = telNumber;
        this.password = password;
        this.role = role;
    }

    @Column(name = "full_name")
    private String fullName;
    @Column(name = "email")
    private String eMail;
    @Column(name = "tel_number")
    private Long telNumber;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;

    @ManyToMany(cascade = { CascadeType.PERSIST,CascadeType.MERGE })
    @JoinTable(
            name = "student_course",
            joinColumns = { @JoinColumn(name = "student_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "course_id", referencedColumnName = "id") }
    )
    @JsonIgnoreProperties("students")
    private Set<Course> courses = new HashSet<>();
}